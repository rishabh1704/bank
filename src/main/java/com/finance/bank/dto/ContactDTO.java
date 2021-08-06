package com.finance.bank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactDTO {
    private UserDTO user;

    private String email;
    private String phone;
}
