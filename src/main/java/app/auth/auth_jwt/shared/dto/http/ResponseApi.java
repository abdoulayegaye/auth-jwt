package app.auth.auth_jwt.shared.dto.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseApi {
    private String resultCode;
    private String resultMessage;
    private boolean error;
    private Object data;
}
