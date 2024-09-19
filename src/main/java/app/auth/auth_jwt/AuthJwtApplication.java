package app.auth.auth_jwt;

import app.auth.auth_jwt.shared.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class AuthJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthJwtApplication.class, args);
	}

	@Autowired
	private EmailService emailService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Bean
	CommandLineRunner start(){
		return args -> {
			System.out.println("http://localhost:8085");
			Map<String, Object> variables = new HashMap<>();
			variables.put("fullName", "Abdoulaye GAYE");
			variables.put("message", "Welcome to our APP!");
			variables.put("otp", "11111111");
			System.out.println("Password : " + passwordEncoder.encode("admin"));
			//emailService.sendHtmlEmail("abgaye@groupeisi.com", "Authentification", "email/email.html", variables);
		};
	}

}
