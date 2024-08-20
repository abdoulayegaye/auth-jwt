package app.auth.auth_jwt.shared.handlers;

import app.auth.auth_jwt.shared.dto.http.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandle {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ResponseApi> handleException(Exception ex) {
        return   ResponseEntity.ok(
                ResponseApi
                        .builder()
                        .error(true)
                        .resultCode("500")
                        .resultMessage(ex.getMessage())
                        .build()
        );

    }
}
