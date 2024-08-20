package app.auth.auth_jwt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "conf")
@Data
public class EnvApp {
    private String jwtSecret;
    private Integer jwtTtl;
    private Integer jwtTtlConfirmation;
    private String jwtPrivateKey;
    private String jwtPublicKey;
    private String appName;
    private String appUrl;
    private String appUrlBackoffice;
    private Integer authTtlOtp;
}
