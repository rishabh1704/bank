package com.finance.bank.validators.impl;

import com.finance.bank.model.Customer;
import com.finance.bank.validators.CustomerValidator;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomerValidatorImpl implements CustomerValidator {
    @Override
    public Pair<Boolean, List<String>> validate(Customer customer) {
        List<String> errors = new ArrayList<>();
        Boolean result = true;

        Pattern namePattern = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
        Matcher firstNameMatcher = namePattern.matcher(customer.getFirstName());
        Matcher lastNameMatcher = namePattern.matcher(customer.getLastName());

        if (firstNameMatcher.find()) {
            errors.add("Special characters and numbers not allowed in first name");
            result = false;
        }

        if (lastNameMatcher.find()) {
            errors.add("\n, Special characters and numbers not allowed in last name");
            result = false;
        }

        return new Pair<>(result, errors);
    }
}
