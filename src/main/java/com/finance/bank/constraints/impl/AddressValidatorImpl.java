package com.finance.bank.constraints.impl;

import com.finance.bank.model.Address;
import com.finance.bank.constraints.AddressValidator;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AddressValidatorImpl implements AddressValidator {
    @Override
    public Pair<Boolean, List<String>> validate(Address address) {
        List<String> errors = new ArrayList<>();
        Boolean result = true;

        Pattern pinPattern = Pattern.compile("^\\d{6}$");
        Matcher pinMatcher = pinPattern.matcher(address.getPinCode());

        if (!pinMatcher.find()) {
            errors.add("Pin code should be of 6 digits and must be numbers.");
            result = false;
        }

        return new Pair<>(result, errors);
    }
}
