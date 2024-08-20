package app.auth.auth_jwt.shared.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;

@MappedSuperclass
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    private Instant createdAt = Instant.now();
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Instant updatedAt = Instant.now();

    @Column(columnDefinition = "bit not null default 1")
    private boolean isEnabled = true;

    private String userCreated;

    private String userUpdated;
    @PreUpdate
    public void preUpdate() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if(username != null){
                userUpdated= username;
            }
        } catch (Exception e){
            System.out.println("ERROR");
        }
    }
    @PrePersist
    public void preSave() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if(username != null){
                userCreated= username;
            }
        } catch (Exception e){
            System.out.println("ERROR");
        }
    }
}
