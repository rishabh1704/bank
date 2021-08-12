package com.finance.bank.controllers;

import com.finance.bank.dto.CustomerDTO;
import com.finance.bank.dto.TransientInfo;
import com.finance.bank.services.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("employees")
@Api(tags = {"Functions related to employee operations"})
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/home")
    public String toHome() {
        return "Welcome to the employee portal.";
    }

    @ApiOperation(value = "create a new customer with default account")
    @PostMapping("/new_customer")
    public ResponseEntity<Object> createNewCustomer(@RequestBody CustomerDTO data) {
        Long id = this.employeeService.createCustomer(data);
        return ResponseEntity.status(HttpStatus.OK).body("Your Customer id is : " + id.toString());
    }

    @ApiOperation(value = "update the customer information")
    @PostMapping("/update_customer/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable(name = "id") Long id, @RequestBody CustomerDTO data) {
        String msg = this.employeeService.updateCustomer(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @ApiOperation(value = "Delete a customer")
    @DeleteMapping("/delete_customer/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(name = "id") Long id) {
        String message = this.employeeService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @ApiOperation(value = "create a new account for a customer")
    @GetMapping("/create_account/{id}")
    public ResponseEntity<Object> createAccount(@PathVariable(name = "id") Long customerId) {
        Long id = this.employeeService.createNewAccount(customerId);
        return ResponseEntity.status(HttpStatus.OK).body("Account created with id : " + id.toString());
    }

    @ApiOperation(value = "delete account for a customer along with all its transactions")
    @PostMapping("/delete_account")
    public ResponseEntity<Object> deleteAccount(@RequestBody TransientInfo data) {
        String msg = this.employeeService.deleteAccount(data);
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @ApiOperation(value = "Logging in the employee.")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody TransientInfo info,  HttpServletResponse response) {
        Cookie cookie = this.employeeService.login(info);
        if (cookie == null) {
            ResponseEntity.status(HttpStatus.OK).body("Cookie not set");
        }
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).body("Logged in.");
    }

}
