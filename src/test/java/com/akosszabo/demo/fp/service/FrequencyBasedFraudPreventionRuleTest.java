package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.FraudCheckCode;
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
        int daysSinceLastTransaction = 5;
        int averageDaysBetweenTransactions = 7;
        final TransactionContext transactionContext = createContextWithTransactions(daysSinceLastTransaction,averageDaysBetweenTransactions);

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertTrue(result.isSuccess());
    }

    @Test
    public void testFailingTransactionComparedToMonthly() {
        int daysSinceLastTransaction = 3;
        int averageDaysBetweenTransactions = 30;
        final TransactionContext transactionContext = createContextWithTransactions(daysSinceLastTransaction,averageDaysBetweenTransactions);

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertFalse(result.isSuccess());
        assertEquals(MESSAGE, result.getMessage());
        assertEquals(FraudCheckCode.FREQUENCY, result.getErrorCode());
    }

    @Test
    public void testSuccessOnEmptyList() {
        final TransactionContext transactionContext = new TransactionContext();
        transactionContext.setTransactionHistory(new ArrayList<>());

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertTrue(result.isSuccess());
    }

    @Test
    public void testSuccessWithDailyList() {
        int daysSinceLastTransaction = 1;
        int averageDaysBetweenTransactions = 0;
        final TransactionContext transactionContext = createContextWithTransactions(daysSinceLastTransaction,averageDaysBetweenTransactions);

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertTrue(result.isSuccess());
    }

    @Test
    public void testFailureWithDailyList() {
        int daysSinceLastTransaction = 2;
        int averageDaysBetweenTransactions = 0;
        final TransactionContext transactionContext = createContextWithTransactions(daysSinceLastTransaction,averageDaysBetweenTransactions);

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertFalse(result.isSuccess());
        assertEquals(MESSAGE, result.getMessage());
        assertEquals(FraudCheckCode.FREQUENCY, result.getErrorCode());
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
