package app.auth.auth_jwt.account.entities;

import app.auth.auth_jwt.shared.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions",uniqueConstraints = {
        @UniqueConstraint(columnNames = "abilityCode"),
        @UniqueConstraint(columnNames = "abilityName"),
})
public class Permission extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String abilityCode;
    private String abilityName;
    private String groupName;
    private String groupCode;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}
