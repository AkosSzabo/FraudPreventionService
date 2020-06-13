package com.akosszabo.demo.fp.persistence;

import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import com.akosszabo.demo.fp.persistence.entity.Transaction;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
public class TransactionDaoImpl implements TransactionDao {

    private TransactionRepository transactionRepository;
    private Converter<Transaction, TransactionDto> transactionToTransactionDtoConverter;

    public TransactionDaoImpl(final TransactionRepository transactionRepository,
                              final Converter<Transaction, TransactionDto> transactionToTransactionDtoConverter) {
        this.transactionRepository = transactionRepository;
        this.transactionToTransactionDtoConverter = transactionToTransactionDtoConverter;
    }

    @Override
    public List<TransactionDto> findLastNTransactionsForAccounts(final String sourceAccount,
                                                                 final String targetAccount,
                                                                 final Integer itemCount) {
        return transactionRepository.findLastNTransactionsForAccounts(sourceAccount, targetAccount, itemCount)
                .stream()
                .map(transaction -> transactionToTransactionDtoConverter.convert(transaction))
                .collect(Collectors.toList());
    }
}
