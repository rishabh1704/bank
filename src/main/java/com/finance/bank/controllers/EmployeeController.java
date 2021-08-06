package com.finance.bank.controllers;

import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
