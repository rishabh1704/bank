package com.finance.bank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashTransactionDTO {
    Long customerId;
    Long accountId;
    Double amount;
}
