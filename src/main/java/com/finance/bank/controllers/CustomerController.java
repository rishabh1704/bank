package com.finance.bank.controllers;

import com.finance.bank.dto.CashTransactionDTO;
import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.dto.TransactionDTO;
import com.finance.bank.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// Todo: add swagger

@RestController
@RequestMapping("customers")
//@Api(tags = { "Accounts and Transactions REST endpoints" })
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping("/home")
    public String toHome() {
        return "Welcome to the banking application.";
    }

    @PostMapping("/balance")
    public Double checkBalance(@RequestBody Long accountId) {
        return this.customerService.balanceEnquiry(accountId);
    }

    @GetMapping("/history")
    public List<TransactionDTO> getTransactionHistory(@RequestBody Long accountId) {
        return this.customerService.transactionHistory(accountId);
    }

    @PostMapping("/transfer_money")
    public ResponseEntity<Object> transferMoney(@RequestBody TransactionDTO transfer) {
        String msg = this.customerService.transferMoney(transfer);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @GetMapping("/profile_info")
    public CustomerDTO viewProfile(@RequestBody Long customerId) {
        return this.customerService.viewPersonalDetails(customerId);
    }

    @GetMapping("/notifications")
    public String getNotifications(@RequestBody Long customerId) {
        return this.customerService.getNotifications(customerId);
    }

    @PostMapping("/add_money")
    public ResponseEntity<Object> addMoney(@RequestBody CashTransactionDTO data) {
        String msg = this.customerService.addFunds(data);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

}
