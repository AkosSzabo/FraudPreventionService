package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.FraudCheckType;
import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import com.akosszabo.demo.fp.persistence.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ParallelFraudPreventionService implements FraudPreventionService {

    @Autowired
    private Map<FraudCheckType, FraudPreventionRule> ruleMap;
    @Autowired
    private TransactionDao transactionDao;

    @Override
    public List<FraudCheckResult> checkTransaction(final TransactionContext transactionContext) {
        final List<TransactionDto> transactions = transactionDao.findLastNTransactionsForAccounts(transactionContext.getUserAccountNumber(), transactionContext.getDestinationAccountNumber(), 5);
        transactionContext.setTransactionHistory(transactions);
        return ruleMap.values().parallelStream().map( rule-> rule.evaluate(transactionContext) ).collect(Collectors.toList());
    }
}
