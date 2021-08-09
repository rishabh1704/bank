package com.finance.bank.services.impl;

import com.finance.bank.dto.TransientInfo;
import com.finance.bank.model.Account;
import com.finance.bank.model.Customer;
import com.finance.bank.repositories.AccountRepository;
import com.finance.bank.repositories.CustomerRepository;
import com.finance.bank.repositories.EmployeeRepository;
import com.finance.bank.services.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public AuthorizationServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository,
                                    EmployeeRepository employeeRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Boolean verifyCustomerAccount(TransientInfo data) {

        Customer customer = this.customerRepository.findCustomerById(data.getCustomerId());

        if (customer == null) {
            return false;
        }

        Account account = this.accountRepository.findAccountById(data.getAccountId());

        if (account == null) {
            return false;
        }

        if (account.getOwner().getId() != customer.getId()) {
            return false;
        }

        return true;
    }

    @Override
    public Boolean verifyEmployeeOperation(TransientInfo info) {
        return null;
    }
}
