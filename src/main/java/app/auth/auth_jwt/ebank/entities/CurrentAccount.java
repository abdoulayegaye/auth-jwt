package app.auth.auth_jwt.ebank.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("CA")
public class CurrentAccount extends BankAccount{
    private double overDraft;
}
