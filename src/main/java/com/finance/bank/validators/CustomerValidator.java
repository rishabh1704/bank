package com.finance.bank.validators;

import com.finance.bank.model.Account;
import com.finance.bank.model.Customer;
import javafx.util.Pair;

import java.util.List;

public interface CustomerValidator {

    public Pair<Boolean, List<String>> validate(Customer customer);

}
