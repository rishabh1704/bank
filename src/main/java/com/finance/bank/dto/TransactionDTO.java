package com.finance.bank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TransactionDTO {

    private Long from;
    private Long to;
    private Double amount;
    private Date txDate;
}
