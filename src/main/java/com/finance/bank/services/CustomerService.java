package com.finance.bank.services;


import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.dto.TransactionDTO;

import java.util.List;

public interface CustomerService {

    public Double balanceEnquiry(Long accountId);

    public List<TransactionDTO> transactionHistory(Long accountId);

    public String transferMoney(TransactionDTO transfer);

    public CustomerDTO viewPersonalDetails(long customerId);

    public String getNotifications(Long customerId);
}
