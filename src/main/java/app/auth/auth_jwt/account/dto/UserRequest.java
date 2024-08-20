package app.auth.auth_jwt.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
public class UserRequest {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
   public  static class AddRequest{
       @NotBlank(message = "Le prénom est requis")
       private String firstname;
       @NotBlank(message = "Le nom est requis")
       private String lastname;
       @NotBlank(message = "L'email est requis")
       @Email(message = "L'email n'est pas au bon format")
       private String email;
       @NotBlank(message = "Le nom d'utilisateur est requis")
       private String username;
       @NotBlank(message = "Le mot de passe est requis")
       private String password;
       @NotBlank(message = "Le téléphone est requis")
       private String phone;
       @NotBlank(message = "Le role est requis")
       private String role;
   }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
   public  static class UpdateRequest{
       @NotNull(message = "L'id est requis")
       private Long id;
       @NotBlank(message = "Le prénom est requis")
       private String firstname;
       @NotBlank(message = "Le nom est requis")
       private String lastname;
       @NotBlank(message = "L'email est requis")
       @Email(message = "L'email n'est pas au bon format")
       private String email;
       @NotBlank(message = "Le nom d'utilisateur est requis")
       private String username;
       @NotBlank(message = "Le mot de passe est requis")
       private String password;
       @NotBlank(message = "Le téléphone est requis")
       private String phone;
       @NotBlank(message = "Le role est requis")
       private String role;
   }
}
