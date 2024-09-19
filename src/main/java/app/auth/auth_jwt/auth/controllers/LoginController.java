package app.auth.auth_jwt.auth.controllers;

import app.auth.auth_jwt.account.annotations.AuthAction;
import app.auth.auth_jwt.auth.abilities.LoginAbility;
import app.auth.auth_jwt.auth.dto.AuthRequest;
import app.auth.auth_jwt.auth.dto.AuthRequestValidation;
import app.auth.auth_jwt.auth.services.AuthService;
import app.auth.auth_jwt.shared.dto.http.ResponseApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(LoginAbility._PATH)
@RequiredArgsConstructor
@Tag(name = LoginAbility._ABILITY_TITLE, description = LoginAbility._ABILITY_DESC)
public class LoginController {

    final AuthService authService;
    @PostMapping("login")
    @Operation(summary = LoginAbility._LOGIN_NAME, description = LoginAbility._LOGIN_NAME)
    @AuthAction(abilityCode = LoginAbility._LOGIN_CODE, abilityName = LoginAbility._LOGIN_NAME, groupName = LoginAbility._GROUP_NAME, groupCode = LoginAbility._GROUP_CODE)
    public ResponseEntity<ResponseApi> login(@Valid @RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(authService.login(authRequest)) ;
    }
    @PostMapping("login-validate-otp")
    @Operation(summary = LoginAbility._LOGIN_OTP_NAME, description = LoginAbility._LOGIN_NAME)
    @AuthAction(abilityCode = LoginAbility._LOGIN_OTP_CODE, abilityName = LoginAbility._LOGIN_OTP_NAME, groupName = LoginAbility._GROUP_NAME, groupCode = LoginAbility._GROUP_CODE)
    public ResponseEntity<ResponseApi> loginValidateOtp(@Valid @RequestBody AuthRequestValidation authRequestValidation){
        return ResponseEntity.ok(authService.loginValidateOtp(authRequestValidation)) ;
    }
}

