package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.FraudCheckCode;
import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.TransactionContext;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountBasedFraudPreventionRule implements FraudPreventionRule {

    public static final String WARNING_MESSAGE = "No previous transactions for this payee";

    @Override
    public FraudCheckResult evaluate(final TransactionContext transactionContext) {
        FraudCheckResult result = FraudCheckResult.createSuccessful();
        if(CollectionUtils.isEmpty(transactionContext.getTransactionHistory())) {
            result = FraudCheckResult.createFailed(WARNING_MESSAGE, FraudCheckCode.ACCOUNT);
        }
        return result;
    }
}
