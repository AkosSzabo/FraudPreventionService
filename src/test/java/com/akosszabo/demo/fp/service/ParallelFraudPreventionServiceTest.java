package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.FraudCheckType;
import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import com.akosszabo.demo.fp.persistence.TransactionDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ParallelFraudPreventionServiceTest {

    @InjectMocks
    private ParallelFraudPreventionService service;
    @Mock
    private Map<FraudCheckType, FraudPreventionRule> ruleMap;
    @Mock
    private TransactionDao transactionDao;
    private FraudPreventionRule rule1;
    private FraudPreventionRule rule2;
    private FraudPreventionRule rule3;

    @Before
    public void init() {
        final Collection<FraudPreventionRule> collectionOfRules = new ArrayList<>();
        rule1 = mock(FraudPreventionRule.class);
        collectionOfRules.add(rule1);
        rule2 = mock(FraudPreventionRule.class);
        collectionOfRules.add(rule2);
        rule3 = mock(FraudPreventionRule.class);
        collectionOfRules.add(rule3);
        when(ruleMap.values()).thenReturn(collectionOfRules);
    }

    @Test
    public void test1() {
        final TransactionContext transactionContext =  new TransactionContext();
        final String account = "account";
        final String payee = "payee";
        transactionContext.setUserAccountNumber(account);
        transactionContext.setDestinationAccountNumber(payee);
        final List<TransactionDto> transactions = mock(List.class);
        when(transactionDao.findLastNTransactionsForAccounts(eq(account),eq(payee),eq(5))).thenReturn(transactions);
        ArgumentCaptor<TransactionContext> rule1Captor = ArgumentCaptor.forClass(TransactionContext.class);
        ArgumentCaptor<TransactionContext>  rule2Captor = ArgumentCaptor.forClass(TransactionContext.class);
        ArgumentCaptor<TransactionContext> rule3Captor = ArgumentCaptor.forClass(TransactionContext.class);
        when(rule1.evaluate(eq(transactionContext))).thenReturn(mock(FraudCheckResult.class));
        when(rule2.evaluate(eq(transactionContext))).thenReturn(mock(FraudCheckResult.class));
        when(rule3.evaluate(eq(transactionContext))).thenReturn(mock(FraudCheckResult.class));

        final List<FraudCheckResult> fraudCheckResults = service.checkTransaction(transactionContext);

        verify(rule1).evaluate(rule1Captor.capture());
        verify(rule2).evaluate(rule2Captor.capture());
        verify(rule3).evaluate(rule3Captor.capture());
        assertEquals(3,fraudCheckResults.size());
        assertEquals(transactions,rule1Captor.getValue().getTransactionHistory());
        assertEquals(transactions,rule2Captor.getValue().getTransactionHistory());
        assertEquals(transactions,rule3Captor.getValue().getTransactionHistory());
    }

}
