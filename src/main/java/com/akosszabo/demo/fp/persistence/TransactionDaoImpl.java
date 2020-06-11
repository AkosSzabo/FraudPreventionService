package com.akosszabo.demo.fp.persistence;

import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import com.akosszabo.demo.fp.entity.Transaction;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Log
@Service
public class TransactionDaoImpl implements TransactionDao {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private Converter<Transaction, TransactionDto> transactionToTransactionDtoConverter;

    private BigDecimal nullSafeConversion(String string) {
        if (StringUtils.isEmpty(string)) {
            return new BigDecimal("0");
        } else {
            return new BigDecimal(string);
        }

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
