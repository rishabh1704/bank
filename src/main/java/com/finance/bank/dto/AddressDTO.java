package com.finance.bank.dto;

import com.finance.bank.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String line1;
    private String line2;
    private String city;
    private String state;
    private String pinCode;
}
