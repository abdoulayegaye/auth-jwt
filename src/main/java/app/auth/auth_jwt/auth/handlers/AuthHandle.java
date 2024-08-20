package app.auth.auth_jwt.auth.handlers;

import app.auth.auth_jwt.shared.dto.http.ResponseApi;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthHandle {
    @ExceptionHandler( BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResponseApi> handleBadLogin() {
      return   ResponseEntity.ok(
              ResponseApi
                      .builder()
                      .error(true)
                      .resultCode("401")
                      .resultMessage("Login et/ou mot de passe incorrect!")
                      .build()
      );
    }
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ResponseApi> handleExpiredToken() {
      return   ResponseEntity.ok(
              ResponseApi
                      .builder()
                      .error(true)
                      .resultCode("403")
                      .resultMessage("Votre session a expiré. Merci de te reconnecter!")
                      .build()
      );
    }


}
