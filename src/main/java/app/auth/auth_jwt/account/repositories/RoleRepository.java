package app.auth.auth_jwt.account.repositories;

import app.auth.auth_jwt.account.entities.Role;
import app.auth.auth_jwt.shared.repositories.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role,Long> {
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);

}
