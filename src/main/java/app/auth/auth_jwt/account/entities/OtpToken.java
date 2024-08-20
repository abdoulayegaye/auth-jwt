package app.auth.auth_jwt.account.entities;

import app.auth.auth_jwt.shared.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "otp_tokens",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"token"}),
            @UniqueConstraint(columnNames = {"token","otp"}),
        })
public class OtpToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String token ;
    private String otp ;
    private String email ;
}
