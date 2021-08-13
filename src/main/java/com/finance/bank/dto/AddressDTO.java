package com.finance.bank.dto;

import com.finance.bank.constraints.PinNumberConstraint;
import com.finance.bank.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class AddressDTO {

    private String line1;
    private String line2;
    private String city;
    private String state;

    @NotNull
    @NotBlank
    @PinNumberConstraint
    private String pinCode;
}
