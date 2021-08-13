package com.finance.bank.dto;

import com.finance.bank.constraints.EmailConstraint;
import com.finance.bank.constraints.OnlyAlphaConstraint;
import com.finance.bank.model.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class CustomerDTO {

    private String customerType;
//    private AccountDTO account;
    @NotNull(message = "First Name can't be empty.")
    @OnlyAlphaConstraint
    private String firstName;

    @NotNull
    @OnlyAlphaConstraint
    private String lastName;
    private String identificationNumber;

    @Valid
    private AddressDTO address;

    @Valid
    private ContactDTO contact;

}
