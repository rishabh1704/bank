package com.finance.bank.validators.impl;

import com.finance.bank.model.Contact;
import com.finance.bank.validators.ContactValidator;
import javafx.util.Pair;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@Component
public class ContactValidatorImpl implements ContactValidator {
    @Override
    public Pair<Boolean, List<String>> validate(Contact contact) {
        List<String> errors = new ArrayList<>();
        Boolean result = true;

        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Pattern phonePattern = Pattern.compile("^\\d{10}$");

        Matcher emailMatcher = emailPattern.matcher(contact.getEmail());
        Matcher phoneMatcher = phonePattern.matcher(contact.getPhone());

        if (!emailMatcher.find()) {
            errors.add("Not a valid email format!");
            result = false;
        }

        log.debug("phone number is");
        log.debug(contact.getPhone());

        if (!phoneMatcher.find()){
            errors.add("Not a valid phone number!");
            result = false;
        }

        return new Pair<>(result, errors);
    }
}
