package com.finance.bank.services.impl;

import com.finance.bank.dto.CashTransactionDTO;
import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.dto.TransactionDTO;
import com.finance.bank.mappers.CustomerToCustomerDTO;
import com.finance.bank.mappers.TransactionToTransactionDTO;
import com.finance.bank.model.Account;
import com.finance.bank.model.Customer;
import com.finance.bank.model.Transaction;
import com.finance.bank.repositories.AccountRepository;
import com.finance.bank.repositories.CustomerRepository;
import com.finance.bank.repositories.TransactionRepository;
import com.finance.bank.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    private final CustomerToCustomerDTO customerToCustomerDTO;
    private final TransactionToTransactionDTO transactionToTransactionDTO;

    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository,
                               TransactionRepository transactionRepository, CustomerToCustomerDTO customerToCustomerDTO,
                               TransactionToTransactionDTO transactionToTransactionDTO) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;

        this.customerToCustomerDTO = customerToCustomerDTO;
        this.transactionToTransactionDTO = transactionToTransactionDTO;
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
            transactionDTO.setTxDate(transaction.getTransactionDate());

            result.add(transactionDTO);
        }

        return result;
    }

//    Todo: show credit or debit alongside.
    @Override
    public String transferMoney(TransactionDTO transfer) {
        Long fromAccount = transfer.getFrom();
        Long toAccount = transfer.getTo();
        Double amount = transfer.getAmount();

        Account fromAccountObj = this.accountRepository.findAccountById(fromAccount);
        Account toAccountObj = this.accountRepository.findAccountById(toAccount);

        if (Double.compare(fromAccountObj.getBalance(), amount) > 0) {
//            transaction can be made
            synchronized (this) {

                Transaction from = new Transaction();
                Transaction to = new Transaction();

                Date curr = new Date();

//                mapping
                from.setAccount(fromAccountObj);
                from.setToAccount(toAccountObj);
                from.setAmount(amount);
                from.setIsNotified(false);
                from.setTransactionDate(curr);
                this.transactionRepository.save(from);

//                mapping
                to.setAccount(toAccountObj);
                to.setToAccount(fromAccountObj);
                to.setAmount(amount);
                to.setIsNotified(false);
                to.setTransactionDate(curr);
                this.transactionRepository.save(to);

                fromAccountObj.setBalance(fromAccountObj.getBalance() - amount);
                this.accountRepository.save(fromAccountObj);

                toAccountObj.setBalance(amount + toAccountObj.getBalance());
                this.accountRepository.save(toAccountObj);

            }


            return "Transaction successful";
        } else {
            return "Insufficient Funds";
        }
    }

    @Override
    public CustomerDTO viewPersonalDetails(long customerId) {
        Customer customer = this.customerRepository.findCustomerById(customerId);
        if (customer == null) {
            return new CustomerDTO();
        }
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO = this.customerToCustomerDTO.convert(customer);
        return customerDTO;
    }

    private List<TransactionDTO> getNotification(Account account) {
        Long id = account.getId();
        List<Transaction> transactions = this.transactionRepository.findAllByAccount_Id(id);
        List<TransactionDTO> result = new ArrayList<>();

        for (Transaction transaction: transactions) {
            if (!transaction.getIsNotified()) {
                transaction.setIsNotified(true);
                this.transactionRepository.save(transaction);
                result.add(this.transactionToTransactionDTO.convert(transaction));
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
            Date at = transactionDTO.getTxDate();

            String adder = "At " + at.toString() + " --- " + "From " + from.toString() + " To " + to.toString() + ", Transaction Amount: " + amount.toString() + "\n";
            result += adder;
        }

        return result;
    }

    @Override
    public String addFunds(CashTransactionDTO data) {
        Customer customer = this.customerRepository.findCustomerById(data.getCustomerId());

        if (customer == null) {
            return "This customer doesn't exist";
        }

        Account account = this.accountRepository.findAccountById(data.getAccountId());

        if (account == null) {
            return "Such an account doesn't exists";
        }

//        Todo: do similar authorization of all other classes.
//        maybe wrap this functionality into a separate class
        if (account.getOwner().getId() != customer.getId()) {
            return "Not authorized to do this transaction";
        }

        Double amount = data.getAmount();

//        create a transaction
        Transaction transaction = new Transaction();

//        cash addition to self from and to account number remains the same.

        transaction.setTransactionDate(new Date());
        transaction.setAmount(amount);
        transaction.setIsNotified(false);
        transaction.setAccount(account);
        transaction.setToAccount(account);
        this.transactionRepository.save(transaction);

        account.setBalance(account.getBalance() + amount);
        this.accountRepository.save(account);

        return "Funds addition successful";
    }

    @Override
    public String withdrawFunds(CashTransactionDTO data) {
        return null;
    }
}
