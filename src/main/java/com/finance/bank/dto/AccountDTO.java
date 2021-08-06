package com.finance.bank.dto;

import com.finance.bank.model.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AccountDTO {

    private String accountNumber;
    private String accountStatus;
    private Double balance;
    private List<TransactionDTO> transactions;
}
