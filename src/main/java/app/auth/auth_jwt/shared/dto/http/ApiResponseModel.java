package app.auth.auth_jwt.shared.dto.http;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder
public class ApiResponseModel<T> {
    String resultMessage;
    Integer resultCode = 200;
    Boolean success = true;
    T data;
    T errors;

    public ApiResponseModel(T data, HttpStatus resultCode, boolean success, String resultMessage) {
        normalize();
        this.data = data;
        this.resultMessage = resultMessage;
        this.success = success;
        this.resultCode = resultCode.value();
    }

    public ApiResponseModel(T data) {
        this.data = data;
        normalize();
    }

    public ApiResponseModel(T data, String resultMessage) {
        this.data = data;
        this.resultMessage = resultMessage;
        normalize();
    }

    public ApiResponseModel(T data, HttpStatus resultCode) {
        this.data = data;
        this.resultCode = resultCode.value();
        normalize();
    }

    public ApiResponseModel(T data, HttpStatus resultCode, boolean success) {
        this.data = data;
        this.success = success;
        this.resultCode = resultCode.value();
        normalize();
    }

    private void normalize() {
        System.out.println("+++++++++++++++++++");
        if (!success && (this.resultMessage == null || this.resultMessage.equals(""))) {
            this.resultMessage = "Une erreur s'est produite";
            this.errors = this.data;
            this.data = null;
        }
        if (success && (this.resultMessage == null || this.resultMessage.equals(""))) {
            this.resultMessage = "Opération terminée sans erreur";
            this.errors = null;
        }
    }
}
