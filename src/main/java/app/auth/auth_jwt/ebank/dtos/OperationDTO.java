package app.auth.auth_jwt.ebank.dtos;

import app.auth.auth_jwt.ebank.enums.TypeOperation;

import java.util.Date;

public record OperationDTO(Long id, Date date, double amount, TypeOperation type) {
}
