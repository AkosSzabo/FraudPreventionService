package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.FraudCheckCode;
import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Log
@Service
public class AmountBasedFraudPreventionRule implements FraudPreventionRule {

    public static final int AMOUNT_LIMIT_MULTIPLIER = 2;
    public static final String WARNING_MESSAGE = "Transaction dollar amount is too high";

    @Override
    public FraudCheckResult evaluate(final TransactionContext transactionContext) {
        FraudCheckResult result = FraudCheckResult.createSuccessful();
        if(transactionContext.getTransactionHistory().size()>0) {
            final BigDecimal averageAmount = transactionContext.getTransactionHistory()
                    .stream().map(TransactionDto::getDollarAmount)
                    .reduce(BigDecimal.ZERO, (a, b) -> a.add(b)).divide(new BigDecimal(transactionContext.getTransactionHistory().size()), RoundingMode.HALF_UP);
            if(isAmountTooHighComparedToAverage(transactionContext.getDollarAmount(), averageAmount)) {
                result = FraudCheckResult.createFailed(WARNING_MESSAGE, FraudCheckCode.AMOUNT);
            }
        }
        return result;
    }

    private boolean isAmountTooHighComparedToAverage(final BigDecimal dollarAmount, final BigDecimal averageAmount) {
        final BigDecimal upperLimit = averageAmount.multiply(new BigDecimal(AMOUNT_LIMIT_MULTIPLIER));
        final boolean result = upperLimit.compareTo(dollarAmount) < 0;
        if(result) {
            log.info("Flagging for transaction amount: " + dollarAmount + " to upper value: " + upperLimit);
        }
        return result;
    }
}
