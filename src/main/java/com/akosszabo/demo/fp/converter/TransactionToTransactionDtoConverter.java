package com.akosszabo.demo.fp.converter;

import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import com.akosszabo.demo.fp.entity.Transaction;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class TransactionToTransactionDtoConverter implements Converter<Transaction, TransactionDto> {

    @Override
    public TransactionDto convert(Transaction transaction) {

        final TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setSourceAccount(transaction.getSourceAccount());
        transactionDto.setTargetAccount(transaction.getTargetAccount());
        transactionDto.setDollarAmount(transaction.getDollarAmount());
        transactionDto.setTransactionDate(transaction.getTransactionDate());
        return transactionDto;

    }
}