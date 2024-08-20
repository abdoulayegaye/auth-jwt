package app.auth.auth_jwt.shared.services;

import app.auth.auth_jwt.config.EnvApp;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    private final EnvApp env;
    @Value("${spring.mail.username}")
    private String username;
    private final List<String> lines = new ArrayList<>();
   /* public EmailService line(String line){
        this.lines.add(line);
        return this;
    }*/

    @Async
    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables)  {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(this.env.getAppName() + "<"+this.username+">");
           // variables.put("lines",this.lines);
            String html = templateEngine.process(templateName, new Context(Locale.getDefault(), variables));
            helper.setText(html, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
    private final String template ="email.html";
    @Async
    public void sendHtmlEmail(String to, String subject ,Map<String, Object> variables) throws MessagingException {
       this.sendHtmlEmail( to,  subject,this.template , variables);
    }
}
