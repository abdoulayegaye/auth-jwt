package app.auth.auth_jwt.account.entities;


import app.auth.auth_jwt.shared.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "phone")
})
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String username;
    @Column(columnDefinition = "bit not null default 0")
    private boolean isAdmin = false ;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(columnDefinition = "bit not null default 1")
    private boolean accountIsEnabled = true ;
    @Column(columnDefinition = "bit not null default 1")
    private boolean accountIsNotExpired  =true;
    @Column(columnDefinition = "bit not null default 1")
    private boolean accountIsNotLocked =true;
    @Column(columnDefinition = "bit not null default 1")
    private boolean credentialNotExpired =true;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountIsNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountIsNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNotExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.accountIsEnabled;
    }
}
