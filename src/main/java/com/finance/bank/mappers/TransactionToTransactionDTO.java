package com.finance.bank.mappers;

import com.finance.bank.dto.TransactionDTO;
import com.finance.bank.model.Transaction;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransactionToTransactionDTO implements Converter<Transaction, TransactionDTO> {
    @Override
    public TransactionDTO convert(Transaction transaction) {
        final TransactionDTO transactionDTO = new TransactionDTO();

        transactionDTO.setTxDate(transaction.getTransactionDate());
        transactionDTO.setFrom(transaction.getAccount().getId());
        transactionDTO.setTo(transaction.getToAccount().getId());
        transactionDTO.setAmount(transaction.getAmount());

        return transactionDTO;
    }
}
