package com.finance.bank.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransientInfo {
    private Long customerId;
    private Long accountId;
    private Long employeeId;
}
