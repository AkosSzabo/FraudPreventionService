package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AmountBasedFraudPreventionRuleTest {

    private AmountBasedFraudPreventionRule rule = new AmountBasedFraudPreventionRule();
    private TransactionContext transactionContext;

    @Before
    public void createContext() {
        transactionContext = new TransactionContext();
        final ArrayList<TransactionDto> transactionHistory = new ArrayList<>();
        transactionHistory.add(new TransactionDto(1L, LocalDateTime.now(), "account1", "payee1", new BigDecimal(1000)));
        transactionHistory.add(new TransactionDto(2L, LocalDateTime.now(), "account1", "payee1", new BigDecimal(1100)));
        transactionHistory.add(new TransactionDto(3L, LocalDateTime.now(), "account1", "payee1", new BigDecimal(4000)));
        transactionHistory.add(new TransactionDto(4L, LocalDateTime.now(), "account1", "payee1", new BigDecimal(2000)));
        transactionHistory.add(new TransactionDto(5L, LocalDateTime.now(), "account1", "payee1", new BigDecimal(700)));
        transactionContext.setTransactionHistory(transactionHistory);
    }

    @Test
    public void testSuccessWithStandardAmount() {
        transactionContext.setDollarAmount(new BigDecimal(2000));

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertTrue(result.isSuccess());
    }

    @Test
    public void testSuccessWithSmallAmount() {
        transactionContext.setDollarAmount(new BigDecimal(2));

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertTrue(result.isSuccess());
    }

    @Test
    public void testFailureWithAmountOverLimit() {
        transactionContext.setDollarAmount(new BigDecimal(3600));

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertFalse(result.isSuccess());
        assertEquals("Transaction dollar amount is too high",result.getMessage());
    }

    @Test
    public void testSuccessOnEmptyList() {
        final TransactionContext transactionContext = new TransactionContext();
        transactionContext.setTransactionHistory(new ArrayList<>());

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertTrue(result.isSuccess());
    }

}
