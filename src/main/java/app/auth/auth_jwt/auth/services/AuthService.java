package app.auth.auth_jwt.auth.services;

import app.auth.auth_jwt.account.dto.UserRequest;
import app.auth.auth_jwt.account.entities.OtpToken;
import app.auth.auth_jwt.account.entities.User;
import app.auth.auth_jwt.account.repositories.OtpTokenRepository;
import app.auth.auth_jwt.account.repositories.RoleRepository;
import app.auth.auth_jwt.account.repositories.UserRepository;
import app.auth.auth_jwt.account.services.UserService;
import app.auth.auth_jwt.auth.dto.*;
import app.auth.auth_jwt.auth.notifications.NotificationService;
import app.auth.auth_jwt.config.EnvApp;
import app.auth.auth_jwt.config.JwtService;
import app.auth.auth_jwt.shared.dto.http.ResponseApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    final UserService userService;
    final RoleRepository roleRepository;
    final JwtService jwtService;
    final UserRepository userRepository;
    final AuthenticationManager authenticationManager ;
    final NotificationService notificationService ;
    final OtpTokenRepository otpTokenRepository ;
    final EnvApp envApp ;

    public AuthResponse register(UserRequest.AddRequest userRequest) {
        User user = this.userService.store(userRequest);
        var token = this.jwtService.generateToken(user);
        return  AuthResponse.builder()
                .tokenValidation(token)
                .expireIn( (System.currentTimeMillis() +this.jwtService.getTTL() )/1000)
                .build();
    }
    public ResponseApi login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
        );
        var user = this.userRepository.findByUsername(authRequest.getUsername()).orElseThrow();
        var otpToken = this.jwtService.getTokenOtp(user.getEmail());
        this.notificationService.sentOtpNotification(user, otpToken.getOtp());
        return  ResponseApi.builder()
                .resultCode("200")
                .resultMessage("Authentification réussie! Veuillez confirmer votre authentification á double facteur!")
                .error(false)
                .data(AuthResponse.builder()
                        .tokenValidation(otpToken.getToken())
                        .expireIn((long) (this.jwtService.getTTLConfirmation() /1000))
                        .build())
                .build();
    }
    public ResponseApi loginValidateOtp(AuthRequestValidation authRequest) {
        Instant now = Instant.now();
        Instant fifteenMinutesAgo = now.minus(Duration.ofMinutes(envApp.getAuthTtlOtp()));
        OtpToken otpToken = this.otpTokenRepository.getOtpToken(authRequest.getToken(),authRequest.getOtp(),fifteenMinutesAgo,true).orElse(null);
        if(otpToken == null){
            return ResponseApi.builder()
                    .resultCode("401")
                    .resultMessage("Le code OTP est incorrect!")
                    .error(true)
                    .data(null)
                    .build();
        }
        otpToken.setEnabled(false);
        this.otpTokenRepository.save(otpToken);
        UserDetails userDetails = this.userRepository.findByEmail(otpToken.getEmail()).orElseThrow();
        return  ResponseApi.builder()
                .resultCode("200")
                .resultMessage("Authentification réussie!")
                .error(false)
                .data(AuthResponseValidation
                        .builder()
                        .token(this.jwtService.generateToken(userDetails))
                        .expireIn((long) ( (System.currentTimeMillis() + this.jwtService.getTTL() )/1000))
                        .build())
                .build();
    }

}

