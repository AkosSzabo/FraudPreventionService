package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.TransactionContext;

import java.util.List;

public interface FraudPreventionService {
    List<FraudCheckResult> checkTransaction(TransactionContext transactionContext);
}
