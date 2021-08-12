package com.finance.bank.bootstrap;

import com.finance.bank.dto.AddressDTO;
import com.finance.bank.dto.ContactDTO;
import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.dto.TransactionDTO;
import com.finance.bank.model.*;
import com.finance.bank.repositories.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Log4j2
@Component
public class BankingBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    // inject repositories
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;
    private TransactionRepository transactionRepository;
    private AddressRepository addressRepository;
    private ContactRepository contactRepository;

    @Autowired
    private TestRepository testRepository;

    public BankingBootstrap(AccountRepository accountRepository, CustomerRepository customerRepository,
                            EmployeeRepository employeeRepository, TransactionRepository transactionRepository,
                            AddressRepository addressRepository, ContactRepository contactRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.transactionRepository = transactionRepository;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (false){
            log.debug("Loading Init Data.");
            getCustomers();
        }

        if (false) {
            log.debug("Init for Employees now.");
            getEmployees();
        }

        if (false) {
            log.debug("Testing the Base entity.");
            test();
        }

        if (false) {
            log.debug("Testing the model mapper");
            testModel();
        }
    }

    void testModel() {
        ModelMapper modelMapper = new ModelMapper();

        Customer customer1 = new Customer();

        Contact contact1 = new Contact();
        contact1.setEmail("rahul@bank.com");
        contact1.setPhone("3049858346");
        contact1.setUser(customer1);

        Address address1 = new Address();
        address1.setCity("Sri Ganganagar");
        address1.setLine1("sadhu wali cantt");
        address1.setLine2("kothi ilaka");
        address1.setState("Rajasthan");
        address1.setPinCode("345678");
        address1.setUser(new HashSet<>(Arrays.asList(customer1)));

        customer1.setFirstName("Rishabh");
        customer1.setLastName("Sharma");
        customer1.setCustomerType(CustomerType.ACTIVE);
        customer1.setIdentificationNumber("random_stuff");
        customer1.setContact(contact1);
        customer1.setAddress(address1);


        CustomerDTO customerDTO = modelMapper.map(customer1, CustomerDTO.class);

        CustomerDTO customerDTO1 = new CustomerDTO(CustomerType.ACTIVE.toString(), "raa", "sing",
                "sdfadfasdfsdfa",
                new AddressDTO("line1111", "line2222", "cityeee", "stateeeee", "123456"),
                new ContactDTO("abcd@fmail.com", "23904820739")
        );

        Customer customer = modelMapper.map(customerDTO1, Customer.class);

        //        individual account1
        Account acc1 = new Account();
        acc1.setOwner(customer1);
        acc1.setAccountNumber("0938509");
        acc1.setAccountType(AccountType.SAVINGS);
        acc1.setBalance(510.22);
        acc1.setId(13L);


//        individual account2
        Account acc2 = new Account();
        acc2.setOwner(customer);
        acc2.setAccountType(AccountType.SALARIED);
        acc2.setBalance(5000.00);
        acc2.setAccountNumber("9869786");
        acc2.setId(15L);

        Transaction transaction = new Transaction();
        transaction.setIsNotified(false);
        transaction.setAmount(9766.9);
        transaction.setTransactionDate(new Date());
        transaction.setAccount(acc1);
        transaction.setToAccount(acc2);

        ModelMapper mapper2 = new ModelMapper();

        PropertyMap<Transaction, TransactionDTO> orderMap = new PropertyMap<Transaction, TransactionDTO>() {
            @Override
            protected void configure() {
                map().setTo(source.getToAccount().getId());
                map().setFrom(source.getAccount().getId());
            }
        };

        mapper2.addMappings(orderMap);

        TransactionDTO transactionDTO = mapper2.map(transaction, TransactionDTO.class);

        log.warn("<<<<<<<<<<<<<  Transaction to TransactionDTO  >>>>>>>>>>>>>>>");
        log.debug(transactionDTO.getAmount());
        log.debug(transactionDTO.getTransactionDate());
        log.debug(transactionDTO.getFrom());
        log.debug(transactionDTO.getTo());

    }

    void test() {
        TestEntity testEntity = new TestEntity();
        testEntity.setF1("kjhk");
        testEntity.setF2("sdfad");

        this.testRepository.save(testEntity);

    }

    void getEmployees() {
        Employee employee = new Employee();
        employee.setPermissions("RW");

        Address address1 = new Address();
        address1.setCity("Sri Ganganagar");
        address1.setLine1("sadhu wali cantt");
        address1.setLine2("kothi ilaka");
        address1.setState("Rajasthan");
        address1.setPinCode("345678");
        this.addressRepository.save(address1);

        employee.setAddress(address1);
        employee.setFirstName("Rahul");
        employee.setLastName("Dravid");
        employee.setIdentificationNumber("some_random_strategy_to_be_decided");

        Contact contact1 = new Contact();
        contact1.setEmail("rahul@bank.com");
        contact1.setPhone("3049858346");
        contact1.setUser(employee);

        this.contactRepository.save(contact1);
        employee.setContact(contact1);

        this.employeeRepository.save(employee);
    }

    void getCustomers() {
        List<Customer> customers = new ArrayList<>(2);

        Address address1 = new Address();
        address1.setCity("Noida");
        address1.setLine1("Vivek vihar");
        address1.setLine2("Sector 82");
        address1.setState("UP");
        address1.setPinCode("201304");
        this.addressRepository.save(address1);

        Address address2 = new Address();
        address2.setCity("New Delhi");
        address2.setLine1("Shubham vihar");
        address2.setLine2("Sector 12");
        address2.setState("Delhi");
        address2.setPinCode("110078");
        this.addressRepository.save(address2);

        Customer customer1 = new Customer();
        customer1.setFirstName("Rishabh");
        customer1.setLastName("Sharma");
        customer1.setIdentificationNumber("sldkfjq34513");
        customer1.setAddress(address1);

        Contact contact1 = new Contact();
        contact1.setEmail("Ris@klsadj.com");
        contact1.setPhone("30498583094");
        contact1.setUser(customer1);

        customer1.setContact(contact1);
        this.contactRepository.save(contact1);

        customer1.setCustomerType(CustomerType.ACTIVE);

//        Set Account
        Set<Account> accounts1 = new HashSet<>();

//        individual account1
        Account acc1 = new Account();
        acc1.setOwner(customer1);
        acc1.setAccountNumber("0938509");
        acc1.setAccountType(AccountType.SAVINGS);
        acc1.setBalance(510.22);


//        individual account2
        Account acc2 = new Account();
        acc2.setOwner(customer1);
        acc2.setAccountType(AccountType.SALARIED);
        acc2.setBalance(5000.00);
        acc2.setAccountNumber("9869786");


//        set of transactions
        Set<Transaction> transactions1 = new HashSet<>();
        Transaction transaction1 = new Transaction();
        transaction1.setAccount(acc2);
        transaction1.setToAccount(acc1);
        transaction1.setAmount(134.90);
        transaction1.setIsNotified(false);
        Date curr = new Date();
        transaction1.setTransactionDate(curr);

        Set<Transaction> transactions2 = new HashSet<>();
        Transaction transaction2 = new Transaction();
        transaction2.setAccount(acc1);
        transaction2.setToAccount(acc2);
        transaction2.setAmount(134.90);
        transaction2.setIsNotified(false);
        transaction2.setTransactionDate(curr);


        this.transactionRepository.save(transaction1);
        this.transactionRepository.save(transaction2);
        transactions1.add(transaction1);
        transactions2.add(transaction2);

        this.accountRepository.save(acc2);
        accounts1.add(acc2);

        this.accountRepository.save(acc1);
        accounts1.add(acc1);

        customer1.setAccount(accounts1);


        this.customerRepository.save(customer1);
        customers.add(customer1);
    }
}
