package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.FraudCheckResult;

public interface FraudPreventionRule {
    FraudCheckResult evaluate(TransactionContext transactionContext);
}
