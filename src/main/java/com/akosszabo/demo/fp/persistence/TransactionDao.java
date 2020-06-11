package com.akosszabo.demo.fp.persistence;

import com.akosszabo.demo.fp.domain.dto.TransactionDto;

import java.util.List;

public interface TransactionDao {
    List<TransactionDto> findLastNTransactionsForAccounts(final String sourceAccount,
                                                          final String targetAccount,
                                                          final Integer itemCount);
}
