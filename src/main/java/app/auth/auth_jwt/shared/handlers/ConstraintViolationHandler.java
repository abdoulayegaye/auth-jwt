package app.auth.auth_jwt.shared.handlers;

import app.auth.auth_jwt.shared.dto.http.ApiResponseModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ConstraintViolationHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseModel<List<ValidationSchema>> handle(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<ValidationSchema> data = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            List<String> msg= new ArrayList<>();
            msg.add(fieldError.getDefaultMessage());
            data.add(ValidationSchema.build(fieldError.getField(), msg));
        }
        return new ApiResponseModel<>(this.groupAndMergeValidationSchemas(data), HttpStatus.BAD_REQUEST,false);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseModel<ValidationSchema> handle(DataIntegrityViolationException e) {
        String message = e.getCause().getCause().getMessage();
        String columnName = message.substring(message.indexOf("'") + 1, message.lastIndexOf("'"));
       // message ="Validation failed: " + columnName + " already exists" ;
        ValidationSchema data = ValidationSchema.build(columnName, Collections.singletonList(message));
        return new ApiResponseModel<>(data, HttpStatus.BAD_REQUEST,false);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseModel<List<ValidationSchema>> handle(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<ValidationSchema> data = violations.stream()
                .map( item -> {
                    List<String> messages= new ArrayList<>();
                    messages.add(item.getMessage());
                    return ValidationSchema.build(String.valueOf(item.getPropertyPath()),messages);
                })
                .toList();
        return new ApiResponseModel<>(this.groupAndMergeValidationSchemas(data), HttpStatus.BAD_REQUEST,false);
    }
    public List<ValidationSchema> groupAndMergeValidationSchemas(List<ValidationSchema> validationSchemas) {
        Map<String, ValidationSchema> map = validationSchemas.stream()
                .collect(Collectors.toMap(ValidationSchema::getName, Function.identity(), (existing, duplicate) -> {
                    existing.getMessages().addAll(duplicate.getMessages());
                    return existing;
                }));
        return new ArrayList<>(map.values());
    }
    @Setter
    @Getter
    @AllArgsConstructor(staticName = "build")
    @NoArgsConstructor
    static public class ValidationSchema {
        String name;
        List<String> messages = new ArrayList<>();
    }
}
