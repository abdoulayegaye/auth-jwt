package app.auth.auth_jwt.ebank.repositories;

import app.auth.auth_jwt.ebank.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
