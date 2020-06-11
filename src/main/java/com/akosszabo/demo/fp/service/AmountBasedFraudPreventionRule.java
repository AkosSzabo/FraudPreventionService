package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.FraudCheckResult;

import java.math.BigDecimal;

public class AmountBasedFraudPreventionRule implements FraudPreventionRule {

    public static final int AMOUNT_LIMIT_MULTIPLIER = 2;
    public static final String FAILURE_MESSAGE = "Transaction dollar amount is too high";

    @Override
    public FraudCheckResult evaluate(final TransactionContext transactionContext) {
        FraudCheckResult result = FraudCheckResult.createSuccessful();
        if(transactionContext.getTransactionHistory().size()>0) {
            final BigDecimal averageAmount = transactionContext.getTransactionHistory()
                    .stream().map(t -> t.getDollarAmount())
                    .reduce(BigDecimal.ZERO, (a, b) -> a.add(b)).divide(new BigDecimal(transactionContext.getTransactionHistory().size()));
            if(isAmountTooHighComparedToAverage(transactionContext.getDollarAmount(), averageAmount)) {
                result = FraudCheckResult.createFailed(FAILURE_MESSAGE);
            }
        }
        return result;
    }

    private boolean isAmountTooHighComparedToAverage(final BigDecimal dollarAmount, final BigDecimal averageAmount) {
        return averageAmount.multiply(new BigDecimal(AMOUNT_LIMIT_MULTIPLIER)).compareTo(dollarAmount)<0;
    }
}
