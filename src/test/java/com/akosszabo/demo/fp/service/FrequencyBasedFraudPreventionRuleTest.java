package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.FraudCheckType;
import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FrequencyBasedFraudPreventionRuleTest {

    public static final String MESSAGE = "The transaction date is outside of the expected range";
    private FrequencyBasedFraudPreventionRule rule = new FrequencyBasedFraudPreventionRule();


    @Test
    public void testSuccessfulWeeklyTransaction() {
        final TransactionContext transactionContext = createContextWithTransactions(5,7);

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertTrue(result.isSuccess());
    }

    @Test
    public void testFailingTransactionComparedToMonthly() {
        final TransactionContext transactionContext = createContextWithTransactions(3,30);

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertFalse(result.isSuccess());
        assertEquals(MESSAGE, result.getMessage());
        assertEquals(FraudCheckType.FREQUENCY, result.getErrorType());
    }

    @Test
    public void testSuccessOnEmptyList() {
        final TransactionContext transactionContext = new TransactionContext();
        transactionContext.setTransactionHistory(new ArrayList<>());

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertTrue(result.isSuccess());
    }

    private static TransactionContext createContextWithTransactions(Integer daysPassedSinceLastTransaction, Integer daysBetweenTransactions) {
        TransactionContext transactionContext = new TransactionContext();
        final ArrayList<TransactionDto> transactionHistory = new ArrayList<>();
        transactionContext.setDateTime(LocalDateTime.now());
        LocalDateTime transactionDate = LocalDateTime.now().minusDays(daysPassedSinceLastTransaction);
        for(long i = 0; i<7;i++) {
            transactionHistory.add(new TransactionDto(i, transactionDate, "account1", "payee1", new BigDecimal(1000)));
            transactionDate = transactionDate.minusDays(daysBetweenTransactions);
        }
        transactionContext.setTransactionHistory(transactionHistory);
        return transactionContext;
    }

}
