package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.FraudCheckCode;
import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.TransactionContext;


public class AccountBasedFraudPreventionRule implements FraudPreventionRule {

    public static final String WARNING_MESSAGE = "No previous transactions for this payee";

    @Override
    public FraudCheckResult evaluate(final TransactionContext transactionContext) {
        FraudCheckResult result = FraudCheckResult.createSuccessful();
        if(transactionContext.getTransactionHistory().size()<1) {
            result = FraudCheckResult.createFailed(WARNING_MESSAGE, FraudCheckCode.ACCOUNT);
        }
        return result;
    }
}
