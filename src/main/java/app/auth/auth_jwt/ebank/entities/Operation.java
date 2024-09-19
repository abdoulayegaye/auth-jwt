package app.auth.auth_jwt.ebank.entities;

import app.auth.auth_jwt.ebank.enums.TypeOperation;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double amount;
    private TypeOperation typeOperation;
    @ManyToOne
    private BankAccount bankAccount;
}
