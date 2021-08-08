package com.finance.bank.controllers;

import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping("/home")
    public String toHome() {
        return "Welcome to the employee portal.";
    }

    @PostMapping("/new_customer")
    public ResponseEntity<Object> createNewCustomer(@RequestBody CustomerDTO data) {
        Long id = this.employeeService.createCustomer(data);
        return ResponseEntity.status(HttpStatus.OK).body("Your Customer id is : " + id.toString());
    }

    @PostMapping("/update_customer/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable(name = "id") Long id, @RequestBody CustomerDTO data) {
        String msg = this.employeeService.updateCustomer(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @DeleteMapping("/delete_customer/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(name = "id") Long id) {
        String message = this.employeeService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/create_account/{id}")
    public ResponseEntity<Object> createAccount(@PathVariable(name = "id") Long customerId) {
        Long id = this.employeeService.createNewAccount(customerId);
        return ResponseEntity.status(HttpStatus.OK).body("Account created with id : " + id.toString());
    }

}
