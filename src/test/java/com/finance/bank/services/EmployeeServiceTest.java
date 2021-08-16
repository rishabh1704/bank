package com.finance.bank.services;

import com.finance.bank.dto.CashTransactionDTO;
import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.dto.TransactionDTO;
import com.finance.bank.model.*;
import com.finance.bank.repositories.AccountRepository;
import com.finance.bank.repositories.CustomerRepository;
import com.finance.bank.repositories.TransactionRepository;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@Log4j2
class EmployeeServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private AccountRepository accountRepository;

    private Customer setUp(Long id) {
        Customer customer = new Customer();
        customer.setFirstName("Rishabh");
        customer.setLastName("Sharma");
        customer.setId(id);

        Address address = new Address();
        address.setPinCode("987897"); address.setState("Delhi"); address.setCity("new Delhi"); address.setLine1("what"); address.setLine2("Line2");
        Contact contact = new Contact();
        contact.setEmail("rishu.info99@gmail.com"); contact.setPhone("8588819741");

        customer.setAddress(address);
        customer.setContact(contact);

        return customer;
    }

    private Account setUpAccount(Customer customer, Long id) {
        Account account = new Account();
        account.setOwner(customer);
        account.setBalance(10000.0);
        account.setId(id);
        account.setAccountNumber("o878uy");
        account.setAccountStatus(AccountType.SAVINGS.toString());

        return account;
    }

    @Test
    @DisplayName("Find customer by id")
    public void testFindById() {
        Customer customer = setUp(14L);

//        called from mock repo directly
        Mockito.doReturn(customer).when(customerRepository).findCustomerById(14L);

//        called from service
        ModelMapper mapper = new ModelMapper();
        Customer returnedCustomer = mapper.map(customerService.viewPersonalDetails(14L), Customer.class);

//        test whether service and repo give same things
        Assertions.assertSame(returnedCustomer.getFirstName(), customer.getFirstName(), "Both objects are not same");
        Assertions.assertSame(returnedCustomer.getLastName(), customer.getLastName(), "Both objects are not same");
        Assertions.assertSame(returnedCustomer.getContact().getEmail(), customer.getContact().getEmail(), "Both emails are not same");
    }

    @Test
    @DisplayName("Check if null on non id query")
    public void testNullById() {
        Customer customer = setUp(14L);
        Mockito.doReturn(customer).when(customerRepository).findCustomerById(14L);

        ModelMapper mapper = new ModelMapper();
        CustomerDTO customerDTO = customerService.viewPersonalDetails(11L);
        Customer returnedCustomer = null;
        if (customerDTO != null)
            returnedCustomer = mapper.map(customerDTO, Customer.class);

        Assertions.assertNull(returnedCustomer.getFirstName(), "Object found surprisingly.");
    }

    @Test
    @DisplayName("Transfer Money")
    public void testTransferMoney() {
        Customer customer = setUp(14l);
        Account acc1 = setUpAccount(customer, 20l);
        Account acc2 = setUpAccount(customer, 21l);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setFrom(20l);
        transactionDTO.setTo(21l);
        transactionDTO.setAmount(100.0);

        Mockito.doReturn(acc1).when(accountRepository).findAccountById(20L);
        Mockito.doReturn(acc2).when(accountRepository).findAccountById(21L);

//        call from service
        String message = customerService.transferMoney(transactionDTO);

        Assertions.assertEquals(accountRepository.findAccountById(20L).getBalance(), 9900.00);
        Assertions.assertEquals(accountRepository.findAccountById(21L).getBalance(), 10100.00);
    }

    @Test
    @DisplayName("Add funds")
    public void testAddFunds() {
        Customer customer = setUp(14l);
        Account account = setUpAccount(customer, 11l);

        CashTransactionDTO cashTransactionDTO = new CashTransactionDTO();
        cashTransactionDTO.setAmount(1200.0);
        cashTransactionDTO.setAccountId(11l);
        cashTransactionDTO.setCustomerId(14l);

        Mockito.doReturn(customer).when(customerRepository).findCustomerById(14l);
        Mockito.doReturn(account).when(accountRepository).findAccountById(11l);

        String message = customerService.addFunds(cashTransactionDTO);

        Assertions.assertEquals(accountRepository.findAccountById(11l).getBalance(), 10000.0 + 1200.0);
    }

    @Test
    @DisplayName("Withdraw Funds")
    public void testWithdrawFunds() {
        Customer customer = setUp(14l);
        Account account = setUpAccount(customer, 11l);

        CashTransactionDTO cashTransactionDTO = new CashTransactionDTO();
        cashTransactionDTO.setAmount(1200.0);
        cashTransactionDTO.setAccountId(11l);
        cashTransactionDTO.setCustomerId(14l);

        Mockito.doReturn(customer).when(customerRepository).findCustomerById(14l);
        Mockito.doReturn(account).when(accountRepository).findAccountById(11l);

        String message = customerService.withdrawFunds(cashTransactionDTO);

        Assertions.assertEquals(accountRepository.findAccountById(11l).getBalance(), 10000.0 - 1200.0);
    }

}