package app.auth.auth_jwt.auth.error;

import java.io.Serial;

//@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedResourceException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public UnauthorizedResourceException(String message) {
        super(message);
    }

    public UnauthorizedResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
