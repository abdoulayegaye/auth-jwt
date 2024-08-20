package app.auth.auth_jwt.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info =@Info(
                title = "Authentification & Authorization 1.0.0",
                version = "1.0.0",
                contact = @Contact(
                        name = "Abdoulaye GAYE",
                        email = "layegaye001@gmail.com",
                        url = "https://www.dev.sn"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "#",
                description = "Service Authentification & Authorization"
        ),
        servers = @Server(
                url = "${conf.app-url}",
                description = "Service Authentification & Authorization"
        )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPISecurityConfiguration {}
