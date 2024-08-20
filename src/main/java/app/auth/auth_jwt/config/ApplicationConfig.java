package app.auth.auth_jwt.config;

import app.auth.auth_jwt.account.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class ApplicationConfig {
    final UserRepository userRepository;
    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    UserDetailsService userDetailsService(){
        return username -> this.userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("L'utilisateur introuvable"));
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider  =  new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
