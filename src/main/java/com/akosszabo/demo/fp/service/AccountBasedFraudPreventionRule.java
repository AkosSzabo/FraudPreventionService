package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.FraudCheckResult;

public class AccountBasedFraudPreventionRule implements FraudPreventionRule {

    public static final String FAILURE_MESSAGE = "No previous transactions for this payee";

    @Override
    public FraudCheckResult evaluate(final TransactionContext transactionContext) {
        FraudCheckResult result = FraudCheckResult.createSuccessful();
        if(transactionContext.getTransactionHistory().size()<1) {
            result = FraudCheckResult.createFailed(FAILURE_MESSAGE);
        }
        return result;
    }
}
