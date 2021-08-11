package com.finance.bank.validators;

import com.finance.bank.model.Account;
import com.finance.bank.model.Contact;
import javafx.util.Pair;

import java.util.List;

public interface ContactValidator {

    public Pair<Boolean, List<String>> validate(Contact contact);

}
