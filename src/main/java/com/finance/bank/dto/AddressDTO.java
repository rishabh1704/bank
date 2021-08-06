package com.finance.bank.dto;

import com.finance.bank.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDTO {

    private UserListDTO user;

    private String line1;
    private String line2;
    private String city;
    private String state;
    private String pinCode;
}
