package com.finance.bank.controllers;

import com.finance.bank.dto.CashTransactionDTO;
import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.dto.LoggingDTO;
import com.finance.bank.dto.TransactionDTO;
import com.finance.bank.logging.LoggingContext;
import com.finance.bank.services.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.spi.http.HttpContext;
import java.util.List;

@RestController
@RequestMapping("customers")
@Api(tags = {"Functions related to customer operations"})
public class CustomerController {

    private CustomerService customerService;

    @PostConstruct
    public void init() {
        System.out.println("before any mapping is called");
//        LoggingContext loggingContext = new LoggingContext();
//        LoggingDTO loggingDTO = new LoggingDTO();
//        LoggingContext.setLoggingInfo(loggingDTO);
    }

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/home")
    public String toHome() {
        return "Welcome to the banking application.";
    }

    @ApiOperation(value = "Check balance of an account")
    @PostMapping("/balance")
    public Double checkBalance(@RequestBody Long accountId) {
        return this.customerService.balanceEnquiry(accountId);
    }

    @ApiOperation(value = "Transaction History for an account")
    @GetMapping("/history")
    public List<TransactionDTO> getTransactionHistory(@RequestBody Long accountId) {
        return this.customerService.transactionHistory(accountId);
    }

    @ApiOperation(value = "Transfer money from one account to another")
    @PostMapping("/transfer_money")
    public ResponseEntity<Object> transferMoney(@RequestBody TransactionDTO transfer) {
        String msg = this.customerService.transferMoney(transfer);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @ApiOperation(value = "View Profile info of a customer")
    @GetMapping("/profile_info")
    public CustomerDTO viewProfile(@RequestBody Long customerId) {
        return this.customerService.viewPersonalDetails(customerId);
    }

    @ApiOperation(value = "Get Unread notifications")
    @GetMapping("/notifications")
    public String getNotifications(@RequestBody Long customerId) {
        return this.customerService.getNotifications(customerId);
    }

    @ApiOperation(value = "Add cash/funds to the account")
    @PostMapping("/add_money")
    public ResponseEntity<Object> addMoney(@RequestBody CashTransactionDTO data) {
        String msg = this.customerService.addFunds(data);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @ApiOperation(value = "Withdraw cash/funds from the account")
    @PostMapping("/withdraw_money")
    public ResponseEntity<Object> withdrawMoney(@RequestBody CashTransactionDTO data, HttpServletRequest request) {
//        LoggingContext loggingContext = new LoggingContext();
//        LoggingDTO loggingDTO = new LoggingDTO();
//        LoggingContext.setLoggingInfo(loggingDTO);
//        LoggingContext.append("request", request.getRequestURI());

        String msg = this.customerService.withdrawFunds(data);

        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

}
