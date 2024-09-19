package app.auth.auth_jwt.ebank.mappers;

import app.auth.auth_jwt.ebank.dtos.CustomerDTO;
import app.auth.auth_jwt.ebank.entities.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    CustomerDTO toDto(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }

    Customer fromDto(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.id());
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        return customer;
    }
}
