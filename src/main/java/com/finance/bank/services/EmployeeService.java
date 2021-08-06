package com.finance.bank.services;

import com.finance.bank.dto.CustomerDTO;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {

    public ResponseEntity<Object> editCustomer(long customerId, CustomerDTO data);

    public ResponseEntity<Object> transferMoney(Long fromAccountId, Long toAccountId, Double amount);

    public ResponseEntity<Object> createCustomer(long customerId, CustomerDTO data);

    public ResponseEntity<Object> deleteCustomer(long customerId);
}
