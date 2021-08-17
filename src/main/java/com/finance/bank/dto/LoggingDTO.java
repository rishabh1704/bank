package com.finance.bank.dto;

import lombok.Data;

@Data
public class LoggingDTO {

    private String path;
    private String response;
    private String request;
    private String statusCode;
    private String responseTime;
    private String amount;
    private String fromAccountId;
    private String toAccountId;

}
