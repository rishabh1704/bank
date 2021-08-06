package com.finance.bank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private String firstName;
    private String lastName;
    private String identificationNumber;

    private AddressDTO address;

    private ContactDTO contact;
}
