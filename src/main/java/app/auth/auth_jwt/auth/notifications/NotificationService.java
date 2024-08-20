package app.auth.auth_jwt.auth.notifications;


import app.auth.auth_jwt.account.entities.User;
import app.auth.auth_jwt.config.EnvApp;
import app.auth.auth_jwt.shared.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmailService emailService;
    private final EnvApp envApp;
    public void sentOtpNotification(User user, String otp){
        Map<String, Object> variables = new HashMap<>();
        variables.put("otp", otp);
        variables.put("Link", envApp.getAppUrlBackoffice());
        variables.put("fullName", user.getFirstname().concat(" ").concat(user.getLastname()) );
        this.emailService.sendHtmlEmail(user.getEmail(), "Authentification", "email/email.html", variables);
    }
}
