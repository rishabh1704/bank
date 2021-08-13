package com.finance.bank.constraints;

import com.finance.bank.model.Address;
import javafx.util.Pair;

import java.util.List;

public interface AddressValidator {

    public Pair<Boolean, List<String>> validate(Address address);
}
