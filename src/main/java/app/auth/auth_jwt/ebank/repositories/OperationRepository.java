package app.auth.auth_jwt.ebank.repositories;

import app.auth.auth_jwt.ebank.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
