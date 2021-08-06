package com.finance.bank.services.impl;

import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.mappers.CustomerDtoToCustomer;
import com.finance.bank.model.*;
import com.finance.bank.repositories.AccountRepository;
import com.finance.bank.repositories.AddressRepository;
import com.finance.bank.repositories.ContactRepository;
import com.finance.bank.repositories.CustomerRepository;
import com.finance.bank.services.EmployeeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    private final CustomerDtoToCustomer customerDtoToCustomer;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public EmployeeServiceImpl(CustomerDtoToCustomer customerDtoToCustomer, AddressRepository addressRepository, ContactRepository contactRepository, CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerDtoToCustomer = customerDtoToCustomer;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    private Account accountCreationUtil(Customer customer) {
        Account account = new Account();
        account.setAccountType(AccountType.SAVINGS);

        Date curr = new Date();
        String acc = AccountType.SAVINGS.toString() + curr.toString();
        account.setAccountNumber(acc);
        account.setOwner(customer);
        account.setBalance(0.0);
        account.setAccountStatus("ACTIVE");

        return account;
    }

    @Override
    public ResponseEntity<Object> updateCustomer(long customerId, CustomerDTO data) {
        return null;
    }

    @Override
    public Long createCustomer(CustomerDTO data) {
//        by default a single account of savings type is opened.
        Customer customer = this.customerDtoToCustomer.convert(data);

//        id is assigned only after saving.
//        using cascade all the child are saved when the parents are saved otherwise if they are not saved and used they will be in transient state. and one transient state can't access other transient state.
        log.debug("customerdto converted to customer");

        Address address = customer.getAddress();
        Contact contact = customer.getContact();

//        address created with link to customer
        this.addressRepository.save(address);
        customer.setAddress(address);

//        contact created with link to customer
        this.contactRepository.save(contact);
        customer.setContact(contact);

//        create account with link to customer
        Account account = accountCreationUtil(customer);
        this.accountRepository.save(account);

//        save customer
        this.customerRepository.save(customer);

        return customer.getId();
    }

    @Override
    public ResponseEntity<Object> deleteCustomer(long customerId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> createNewAccount(long customerId) {
        return null;
    }
}
