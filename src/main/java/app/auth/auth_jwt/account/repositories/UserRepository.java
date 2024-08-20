package app.auth.auth_jwt.account.repositories;

import app.auth.auth_jwt.account.entities.User;
import app.auth.auth_jwt.shared.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    @Query("SELECT COUNT(u) > 0 FROM User u JOIN u.role r JOIN r.permissions p WHERE u.id = :userId AND p.abilityCode = :permissionCode")
    boolean hasPermission(@Param("userId") Long userId, @Param("permissionCode") String permissionCode);
}
