package com.finance.bank.services;

import com.finance.bank.dto.CustomerDTO;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {

    public String updateCustomer(long customerId, CustomerDTO data);

    public Long createCustomer(CustomerDTO data);

    public String deleteCustomer(long customerId);

    public Long createNewAccount(long customerId);

    public String deleteAccount(Long customerId, Long accountId);
}
