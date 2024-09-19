package app.auth.auth_jwt.ebank.dtos;

import app.auth.auth_jwt.ebank.enums.AccountStatus;

import java.util.Date;

public record BankAccountDTO(String id, Date createdAt, double balance, AccountStatus status, String currency) {
}
