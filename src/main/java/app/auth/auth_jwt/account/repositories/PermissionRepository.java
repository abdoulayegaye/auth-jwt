package app.auth.auth_jwt.account.repositories;

import app.auth.auth_jwt.account.entities.Permission;
import app.auth.auth_jwt.shared.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends BaseRepository<Permission,Long> {
    Optional<Permission> findByAbilityCode(String name);
}
