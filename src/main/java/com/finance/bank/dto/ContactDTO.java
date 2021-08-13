package com.finance.bank.dto;

import com.finance.bank.constraints.EmailConstraint;
import com.finance.bank.constraints.PhoneNumberConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class ContactDTO {
//    private UserDTO user;

    @EmailConstraint
    private String email;

    @PhoneNumberConstraint
    private String phone;
}
