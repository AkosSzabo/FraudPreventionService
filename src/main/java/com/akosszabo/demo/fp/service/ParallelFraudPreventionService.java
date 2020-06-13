package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.FraudCheckCode;
import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import com.akosszabo.demo.fp.persistence.TransactionDao;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log
@Service
public class ParallelFraudPreventionService implements FraudPreventionService {

    public static final int TRANSACTIONS_TO_FETCH = 5;
    private List<FraudPreventionRule> ruleList;
    private TransactionDao transactionDao;

    @Autowired
    public ParallelFraudPreventionService(final TransactionDao transactionDao, final List<FraudPreventionRule> ruleList) {
        this.transactionDao = transactionDao;
        this.ruleList = ruleList;
    }

    @Override
    public List<FraudCheckResult> checkTransaction(final TransactionContext transactionContext) {
        final List<TransactionDto> transactions = transactionDao.findLastNTransactionsForAccounts(transactionContext.getUserAccountNumber(), transactionContext.getPayeeAccountNumber(), TRANSACTIONS_TO_FETCH);
        transactionContext.setTransactionHistory(transactions);
        return ruleList.parallelStream().map(rule -> rule.evaluate(transactionContext)).collect(Collectors.toList());
    }
}
