package app.auth.auth_jwt.shared.error;

import java.io.Serial;

public class BadFilterException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public BadFilterException(String message) {
        super(message);
    }

    public BadFilterException(String message, Throwable cause) {
        super(message, cause);
    }
}
