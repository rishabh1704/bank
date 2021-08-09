package com.finance.bank.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransientInfo {
    private Long customerId;
    private Long accountId;
    private Long employeeId;
}
