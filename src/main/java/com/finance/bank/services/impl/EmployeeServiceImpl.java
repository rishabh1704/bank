package com.finance.bank.services.impl;

import com.finance.bank.constraints.*;
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
import javafx.util.Pair;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    private final CustomerDtoToCustomer customerDtoToCustomer;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    private final AuthorizationService authorizationService;

    private final ContactValidator contactValidator;
    private final CustomerValidator customerValidator;
    private final AddressValidator addressValidator;

    public EmployeeServiceImpl(CustomerDtoToCustomer customerDtoToCustomer, AddressRepository addressRepository,
                               ContactRepository contactRepository, CustomerRepository customerRepository,
                               AccountRepository accountRepository, AuthorizationService authorizationService,
                               ContactValidator contactValidator, AddressValidator addressValidator, CustomerValidator customerValidator) {
        this.customerDtoToCustomer = customerDtoToCustomer;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;

        this.authorizationService = authorizationService;

        this.contactValidator = contactValidator;
        this.customerValidator = customerValidator;
        this.addressValidator = addressValidator;
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

    private String consolidateErrors(List<String> errors) {
        String msg = "";
        for (String m : errors) {
            msg += m;
            msg += "\n";
        }

        return msg;
    }

    private String generateIdentificationNumber(String fname) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String id = fname.toUpperCase() + timeStamp;
        return id;
    }

    @Override
    public String updateCustomer(long customerId, CustomerDTO data) {
        Customer customer = this.customerRepository.findCustomerById(customerId);
        if (customer == null) {
//            customer not found
            return "No such customer exists in our database";
        }

        ModelMapper mapper = new ModelMapper();

//        Customer newCustomer = this.customerDtoToCustomer.convert(data);
        Customer newCustomer = mapper.map(data, Customer.class);

//        Identification number can never be changed even in the update
//        Manually set all fields and then update in db as if the whole new object is saved then the mappings are forgotten
//        and new mappings must be found

//        if (false) {
//            Pair<Boolean, List<String>> val1 = this.addressValidator.validate(newCustomer.getAddress());
//            Pair<Boolean, List<String>> val2 = this.customerValidator.validate(newCustomer);
//            Pair<Boolean, List<String>> val3 = this.contactValidator.validate(newCustomer.getContact());
//
//            log.debug(val1.getKey());
//            log.debug(val2.getKey());
//            log.debug(val3.getKey());
//
//            if (!val1.getKey() || !val2.getKey() || !val3.getKey()) {
//                List<String> errors = new ArrayList<>();
//                errors.addAll(val1.getValue());
//                errors.addAll(val2.getValue());
//                errors.addAll(val3.getValue());
//                return consolidateErrors(errors);
//            }
//        }

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
        ModelMapper mapper = new ModelMapper();
//        Customer customer = this.customerDtoToCustomer.convert(data);
        Customer customer = mapper.map(data, Customer.class);
        Address address = customer.getAddress();
        Contact contact = customer.getContact();

//        validation
//        Pair<Boolean, List<String>> val1 = this.addressValidator.validate(address);
//        Pair<Boolean, List<String>> val2 = this.customerValidator.validate(customer);
//        Pair<Boolean, List<String>> val3 = this.contactValidator.validate(contact);
//
//        if (!val1.getKey() || !val2.getKey() || !val3.getKey()) {
//            List<String> errors = new ArrayList<>();
//            errors.addAll(val1.getValue());
//            errors.addAll(val2.getValue());
//            errors.addAll(val3.getValue());
//            log.debug("Account creation errors!");
//            for (String err: errors) {
//                log.debug(err);
//            }
//            return -1L;
//        }

//        id is assigned only after saving.
//        using cascade all the child are saved when the parents are saved otherwise if they are not saved and used they will be in transient state. and one transient state can't access other transient state.
        log.debug("customerdto converted to customer");
        customer.setIdentificationNumber(generateIdentificationNumber(customer.getFirstName()));

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

    @Override
    public Cookie login(TransientInfo info) {
        Boolean val = this.authorizationService.verifyEmployeeOperation(info);
        if (!val)
            return null;
        Cookie cookie = new Cookie("employeeId", info.getEmployeeId().toString());
        cookie.setMaxAge(1000);
        return cookie;
    }
}
