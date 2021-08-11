package com.finance.bank.services.impl;

import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.dto.TransientInfo;
import com.finance.bank.mappers.CustomerDtoToCustomer;
import com.finance.bank.model.*;
import com.finance.bank.repositories.AccountRepository;
import com.finance.bank.repositories.AddressRepository;
import com.finance.bank.repositories.ContactRepository;
import com.finance.bank.repositories.CustomerRepository;
import com.finance.bank.services.AuthorizationService;
import com.finance.bank.services.EmployeeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    private final CustomerDtoToCustomer customerDtoToCustomer;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    private final AuthorizationService authorizationService;

    public EmployeeServiceImpl(CustomerDtoToCustomer customerDtoToCustomer, AddressRepository addressRepository,
                               ContactRepository contactRepository, CustomerRepository customerRepository,
                               AccountRepository accountRepository, AuthorizationService authorizationService) {
        this.customerDtoToCustomer = customerDtoToCustomer;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;

        this.authorizationService = authorizationService;
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
    @Transactional
    public String updateCustomer(long customerId, CustomerDTO data) {
        Customer customer = this.customerRepository.findCustomerById(customerId);
        if (customer == null) {
//            customer not found
            return "No such customer exists in our database";
        }

//        Identification number can never be changed even in the update
//        Manually set all fields and then update in db as if the whole new object is saved then the mappings are forgotten
//        and new mappings must be found

//        Todo: validation is required, and send which fields can not be changed to the employee
        customer.setCustomerType(CustomerType.valueOf(data.getCustomerType()));
        customer.setFirstName(data.getFirstName());
        customer.setLastName(data.getLastName());

//        setting the address fields
        customer.getAddress().setLine1(data.getAddress().getLine1());
        customer.getAddress().setLine2(data.getAddress().getLine2());
        customer.getAddress().setCity(data.getAddress().getCity());
        customer.getAddress().setState(data.getAddress().getState());
        customer.getAddress().setPinCode(data.getAddress().getPinCode());

//        setting the contact fields
        customer.getContact().setEmail(data.getContact().getEmail());
        customer.getContact().setPhone(data.getContact().getPhone());

//        account is managed internally so can't be changed on user's request

        this.customerRepository.save(customer);

        return "Updated the information Successfully!";
    }

    @Override
    @Transactional
    public Long createCustomer(CustomerDTO data) {
//        by default a single account of savings type is opened.
        Customer customer = this.customerDtoToCustomer.convert(data);

//        id is assigned only after saving.
//        using cascade all the child are saved when the parents are saved otherwise if they are not saved and used they will be in transient state. and one transient state can't access other transient state.
        log.debug("customerdto converted to customer");

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String id = customer.getFirstName().toUpperCase() + timeStamp;
        customer.setIdentificationNumber(id);

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
    public String deleteCustomer(long customerId) {
        Customer customer = this.customerRepository.findCustomerById(customerId);
        if (customer == null) {
            return "Can't find the customer you are looking for.";
        }

        this.customerRepository.delete(customer);

        return "The customer has been wiped from the database.";
    }

    @Override
    public Long createNewAccount(long customerId) {
        Customer customer = this.customerRepository.findCustomerById(customerId);
        if (customer == null) {
            return -1L;
        }

        Account account = accountCreationUtil(customer);
        this.accountRepository.save(account);

        return account.getId();
    }

    @Override
    public String deleteAccount(TransientInfo info) {
        if (!this.authorizationService.verifyEmployeeOperation(info) ||
                !this.authorizationService.verifyCustomerAccount(info))
            return "Not Authorized.";

        Customer customer = this.customerRepository.findCustomerById(info.getCustomerId());
        Account account = this.accountRepository.findAccountById(info.getAccountId());

        this.accountRepository.delete(account);

        return "Account wiped!";
    }
}
