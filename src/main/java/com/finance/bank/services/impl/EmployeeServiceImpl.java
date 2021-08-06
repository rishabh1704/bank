package com.finance.bank.services.impl;

import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public ResponseEntity<Object> editCustomer(long customerId, CustomerDTO data) {
        return null;
    }

    @Override
    public ResponseEntity<Object> transferMoney(Long fromAccountId, Long toAccountId, Double amount) {
        return null;
    }

    @Override
    public ResponseEntity<Object> createCustomer(long customerId, CustomerDTO data) {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteCustomer(long customerId) {
        return null;
    }
}
