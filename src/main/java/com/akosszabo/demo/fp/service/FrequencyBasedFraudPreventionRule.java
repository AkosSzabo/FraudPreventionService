package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.FraudCheckCode;
import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import com.akosszabo.demo.fp.util.DateUtil;
import com.akosszabo.demo.fp.util.LocalDateTimeFrequencyCollector;
import org.springframework.stereotype.Service;

@Service
public class FrequencyBasedFraudPreventionRule implements FraudPreventionRule {
    public static final String WARNING_MESSAGE = "The transaction date is outside of the expected range";
    public static final int LOWER_LIMIT_DENOMINATOR = 2;
    public static final int UPPER_LIMIT_MULTIPLIER = 2;

    @Override
    public FraudCheckResult evaluate(final TransactionContext transactionContext) {
        FraudCheckResult result = FraudCheckResult.createSuccessful();
        if(transactionContext.getTransactionHistory().size()>1) {
            final int averageDifferenceInDays = transactionContext.getTransactionHistory().stream().map(TransactionDto::getTransactionDate).collect(LocalDateTimeFrequencyCollector.getCollector()).getFrequency();
            final int calculateUpperLimit = calculateUpperLimit(averageDifferenceInDays);
            final int calculateLowerLimit = calculateLowerLimit(averageDifferenceInDays);
            final int daysPassed = DateUtil.calculateDaysBetweenLocalDateTimes(transactionContext.getTransactionHistory().get(0).getTransactionDate(),transactionContext.getDateTime());
            if(calculateUpperLimit<daysPassed||daysPassed<calculateLowerLimit) {
                result = FraudCheckResult.createFailed(WARNING_MESSAGE, FraudCheckCode.FREQUENCY);
            }
        }
        return result;
    }

    private int calculateLowerLimit(final int days) {
        return days / LOWER_LIMIT_DENOMINATOR;
    }

    private int calculateUpperLimit(final int days) {
        int result = 1;
        if(days>0L) {
            result = days * UPPER_LIMIT_MULTIPLIER+1;
        }
        return result;
    }

}
