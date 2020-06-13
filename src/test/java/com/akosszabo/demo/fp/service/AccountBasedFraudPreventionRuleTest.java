package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.FraudCheckCode;
import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AccountBasedFraudPreventionRuleTest {

    public static final String MESSAGE = "No previous transactions for this payee";
    private AccountBasedFraudPreventionRule rule = new AccountBasedFraudPreventionRule();

    @Test
    public void testFailureOnEmptyList() {
        final TransactionContext transactionContext = new TransactionContext();
        transactionContext.setTransactionHistory(new ArrayList<>());

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertFalse(result.isSuccess());
        assertEquals(MESSAGE,result.getMessage());
        assertEquals(FraudCheckCode.ACCOUNT, result.getErrorCode());
    }

    @Test
    public void testSuccessOnListIsNotEmpty() {
        final TransactionContext transactionContext = new TransactionContext();
        transactionContext.setTransactionHistory(Arrays.asList(new TransactionDto()));

        final FraudCheckResult result = rule.evaluate(transactionContext);

        assertTrue(result.isSuccess());
    }

}
