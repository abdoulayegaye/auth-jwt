package app.auth.auth_jwt.shared.handlers;

import app.auth.auth_jwt.shared.dto.http.ResponseApi;
import app.auth.auth_jwt.shared.error.BadFilterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadFilterHandler {
    @ExceptionHandler(BadFilterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseApi> handle(BadFilterException e) {
        return   ResponseEntity.ok(
                ResponseApi
                        .builder()
                        .error(true)
                        .resultCode("400")
                        .resultMessage(e.getMessage())
                        .build()
        );
    }
}
