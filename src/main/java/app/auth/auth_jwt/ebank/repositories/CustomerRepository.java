package app.auth.auth_jwt.ebank.repositories;

import app.auth.auth_jwt.ebank.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
