package com.finance.bank.dto;

import com.finance.bank.model.CustomerType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {

    private String customerType;
    private AccountDTO account;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private AddressDTO address;
    private ContactDTO contact;
}
