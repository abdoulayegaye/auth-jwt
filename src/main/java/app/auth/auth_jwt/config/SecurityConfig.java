package app.auth.auth_jwt.config;

import app.auth.auth_jwt.shared.dto.http.ResponseApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    final JwtFilterAuthentification jwtFilterAuthentification;
    final AuthenticationProvider authProvider;
    private final String[] authorizedUrl =  new String[]{"/api/v1/auth/**", "/swagger-ui**", "swagger-ui/**", "/v3/api-docs/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
             .disable()
             .authorizeHttpRequests()
             .requestMatchers(authorizedUrl)
             .permitAll()
             .anyRequest()
             .permitAll()
             .and()
             .sessionManagement()
             .sessionCreationPolicy( SessionCreationPolicy.STATELESS)
             .and()
             .exceptionHandling()
             .authenticationEntryPoint((request, response, accessDeniedException) -> {
                 String jsonErrorResponse = new ObjectMapper()
                         .writeValueAsString(ResponseApi
                                 .builder()
                                 .error(true)
                                 .resultCode("401")
                                 .resultMessage("Authentification requise")
                                 .build()
                         );
                 response.setStatus(HttpStatus.FORBIDDEN.value());
                 response.setContentType("application/json");
                 response.getWriter().write(jsonErrorResponse);
             })
             .and()
             .authenticationProvider(authProvider)
             .addFilterBefore(this.jwtFilterAuthentification, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
