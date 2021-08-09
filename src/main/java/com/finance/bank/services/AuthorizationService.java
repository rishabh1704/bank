package com.finance.bank.services;

import com.finance.bank.dto.TransientInfo;

public interface AuthorizationService {
    public Boolean verifyCustomerAccount(TransientInfo info);

    public Boolean verifyEmployeeOperation(TransientInfo info);
}
