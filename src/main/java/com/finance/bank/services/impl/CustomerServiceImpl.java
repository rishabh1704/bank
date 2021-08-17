package com.finance.bank.services.impl;

import com.finance.bank.dto.*;
import com.finance.bank.exceptions.InvalidCredentialsException;
import com.finance.bank.logging.LoggingContext;
import com.finance.bank.model.Account;
import com.finance.bank.model.Customer;
import com.finance.bank.model.Transaction;
import com.finance.bank.repositories.AccountRepository;
import com.finance.bank.repositories.CustomerRepository;
import com.finance.bank.repositories.TransactionRepository;
import com.finance.bank.services.AuthorizationService;
import com.finance.bank.services.CustomerService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AuthorizationService authorizationService;

    private final Logger LOGGER = LoggerFactory.getLogger("LOGGING");

    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository,
                               TransactionRepository transactionRepository, AuthorizationService authorizationService) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    public Double balanceEnquiry(Long accountId) {
        Account account = this.accountRepository.findAccountById(accountId);
        return account.getBalance();
    }

    @Override
    public List<TransactionDTO> transactionHistory(Long accountId) {
        List<TransactionDTO> result = new ArrayList<TransactionDTO>();
        List<Transaction> transactions = this.transactionRepository.findAllByAccount_Id(accountId);

        for (Transaction t : transactions) {
            log.debug("Transaction \n");
            log.debug(t.getAmount().toString());
        }

//        separate out the mapping

        for (Transaction transaction: transactions) {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setAmount(transaction.getAmount());
            transactionDTO.setFrom(transaction.getAccount().getId());
            transactionDTO.setTo(transaction.getToAccount().getId());
            transactionDTO.setTransactionDate(transaction.getTransactionDate());

            result.add(transactionDTO);
        }

        return result;
    }

    private void createAndSaveTransaction(Account from, Account to, Double amount, Date curr) {

        Transaction transaction = new Transaction();

        transaction.setAccount(from);
        transaction.setToAccount(to);
        transaction.setAmount(amount);
        transaction.setIsNotified(false);
        transaction.setTransactionDate(curr);

        LoggingDTO loggingDTO = new LoggingDTO();
        LoggingContext.setLoggingInfo(loggingDTO);
        LoggingContext.append("fromAccountId", from.getId());
        LoggingContext.append("toAccountId", to.getId());
        LoggingContext.append("amount", amount.toString());

        LOGGER.info(LoggingContext.getLoggingInfo().toString());

        this.transactionRepository.save(transaction);


    }

    @Override
    @Transactional
    public String transferMoney(TransactionDTO transfer) {
        Long fromAccount = transfer.getFrom();
        Long toAccount = transfer.getTo();
        Double amount = transfer.getAmount();

        synchronized (this) {
            Account fromAccountObj = this.accountRepository.findAccountById(fromAccount);
            Account toAccountObj = this.accountRepository.findAccountById(toAccount);

            if (Double.compare(fromAccountObj.getBalance(), amount) > 0) {
//            transaction can be made

                Date curr = new Date();

                createAndSaveTransaction(fromAccountObj, toAccountObj, -1.0 * amount, curr);
                createAndSaveTransaction(toAccountObj, fromAccountObj, amount, curr);

                fromAccountObj.setBalance(fromAccountObj.getBalance() - amount);
                this.accountRepository.save(fromAccountObj);

                toAccountObj.setBalance(amount + toAccountObj.getBalance());
                this.accountRepository.save(toAccountObj);

                return "Transaction successful";
            } else
                return "Insufficient Funds";
        }
    }

    @Override
    public CustomerDTO viewPersonalDetails(long customerId) {
        Customer customer = this.customerRepository.findCustomerById(customerId);
        ModelMapper modelMapper = new ModelMapper();
        if (customer == null) {
            return new CustomerDTO();
        }
        CustomerDTO customerDTO = new CustomerDTO();

//        customerDTO = this.customerToCustomerDTO.convert(customer);
        customerDTO = modelMapper.map(customer, CustomerDTO.class);
        return customerDTO;
    }

    private List<TransactionDTO> getNotification(Account account) {
        Long id = account.getId();
        List<Transaction> transactions = this.transactionRepository.findAllByAccount_Id(id);
        List<TransactionDTO> result = new ArrayList<>();

//        Configuration for mapping account to ids
        PropertyMap<Transaction, TransactionDTO> orderMap = new PropertyMap<Transaction, TransactionDTO>() {
            @Override
            protected void configure() {
                map().setTo(source.getToAccount().getId());
                map().setFrom(source.getAccount().getId());
            }
        };

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(orderMap);


        for (Transaction transaction: transactions) {
            if (!transaction.getIsNotified()) {
                transaction.setIsNotified(true);
                this.transactionRepository.save(transaction);
                result.add(mapper.map(transaction, TransactionDTO.class));
//                result.add(this.transactionToTransactionDTO.convert(transaction));
            }
        }

        return result;
    }

    @Override
    public String getNotifications(Long customerId) {
        List<Account> accounts = this.accountRepository.findAccountsByOwner_Id(customerId);
        List<TransactionDTO> transactionDTOS = new ArrayList<>();

        String result = "";

        for (Account acc : accounts) {
            transactionDTOS.addAll(getNotification(acc));
        }

//        generate notifications
        for (TransactionDTO transactionDTO: transactionDTOS) {
            Long from = transactionDTO.getFrom();
            Long to = transactionDTO.getTo();
            Double amount = transactionDTO.getAmount();
            Date at = transactionDTO.getTransactionDate();

            String adder = "At " + at.toString() + " --- " + "From " + from.toString() + " To " + to.toString() + ", Transaction Amount: " + amount.toString() + "\n";
            result += adder;
        }

        return result;
    }

    @Override
    @Transactional
    public synchronized String addFunds(CashTransactionDTO data) {
        TransientInfo info = new TransientInfo(data.getCustomerId(), data.getAccountId(), null, null);
//        if (!this.authorizationService.verifyCustomerAccount(info)) return "Not Authorized";
        if (!this.authorizationService.verifyCustomerAccount(info)) throw new InvalidCredentialsException("Credentials not enough. Addition not possible.");

        Customer customer = this.customerRepository.findCustomerById(data.getCustomerId());
        Account account = this.accountRepository.findAccountById(data.getAccountId());

        Double amount = data.getAmount();

//        cash addition to self from and to account number remains the same.

        createAndSaveTransaction(account, account, amount, new Date());

//        update balance
        account.setBalance(account.getBalance() + amount);
        this.accountRepository.save(account);

        return "Funds addition successful";
    }

    @Override
    @Transactional
    public synchronized String withdrawFunds(CashTransactionDTO data) {
        TransientInfo info = new TransientInfo(data.getCustomerId(), data.getAccountId(), null, null);
//        if (!this.authorizationService.verifyCustomerAccount(info)) return "Not Authorized";

        if (!this.authorizationService.verifyCustomerAccount(info)) throw new InvalidCredentialsException("Creds not enough. Withdrawal not possible.");


        Customer customer = this.customerRepository.findCustomerById(data.getCustomerId());
        Account account = this.accountRepository.findAccountById(data.getAccountId());
        Double amount = data.getAmount();
        Double balance = account.getBalance();

        if (Double.compare(amount, balance) > 0) {
            return "Insufficient funds.";
        }

        createAndSaveTransaction(account, account, -1*amount, new Date());

        account.setBalance(account.getBalance() - amount);
        this.accountRepository.save(account);

        return "Funds withdrawal successful";
    }
}
