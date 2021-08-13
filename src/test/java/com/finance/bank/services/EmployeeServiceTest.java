package com.finance.bank.services;

import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.model.Address;
import com.finance.bank.model.Contact;
import com.finance.bank.model.Customer;
import com.finance.bank.repositories.CustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    private Customer setUp() {
        Customer customer = new Customer();
        customer.setFirstName("Rishabh");
        customer.setLastName("Sharma");
        customer.setId(14L);

        Address address = new Address();
        address.setPinCode("987897"); address.setState("Delhi"); address.setCity("new Delhi"); address.setLine1("what"); address.setLine2("Line2");
        Contact contact = new Contact();
        contact.setEmail("rishu.info99@gmail.com"); contact.setPhone("8588819741");

        customer.setAddress(address);
        customer.setContact(contact);

        return customer;
    }

    @Test
    @DisplayName("Find customer by id")
    public void testFindById() {
        Customer customer = setUp();

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
        Customer customer = setUp();
        Mockito.doReturn(customer).when(customerRepository).findCustomerById(14L);

        ModelMapper mapper = new ModelMapper();
        CustomerDTO customerDTO = customerService.viewPersonalDetails(11L);
        Customer returnedCustomer = null;
        if (customerDTO != null)
            returnedCustomer = mapper.map(customerDTO, Customer.class);

        Assertions.assertNull(returnedCustomer.getFirstName(), "Object found surprisingly.");
    }

}