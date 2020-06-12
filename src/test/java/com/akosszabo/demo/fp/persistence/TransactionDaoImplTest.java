package com.akosszabo.demo.fp.persistence;

import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import com.akosszabo.demo.fp.entity.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionDaoImplTest {

    @InjectMocks
    private TransactionDaoImpl transactionDaoImpl;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private Converter<Transaction, TransactionDto> transactionToTransactionDtoConverter;

    private final String account = "account1";
    private final String payee = "account2";
    private final Integer itemCount = 3;

    @Test
    public void testFindLastNTransactionsForAccounts() {
        final Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        final Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        final Transaction transaction3 = new Transaction();
        transaction3.setId(3L);
        final List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);
        when(transactionRepository.findLastNTransactionsForAccounts(eq(account),eq(payee),eq(itemCount))).thenReturn(transactions);
        final TransactionDto transactionDto1 = new TransactionDto();
        transactionDto1.setSourceAccount("acc1");
        final TransactionDto transactionDto2 = new TransactionDto();
        transactionDto2.setSourceAccount("acc2");
        final TransactionDto transactionDto3 = new TransactionDto();
        transactionDto3.setSourceAccount("acc3");
        when(transactionToTransactionDtoConverter.convert(eq(transaction1))).thenReturn(transactionDto1);
        when(transactionToTransactionDtoConverter.convert(eq(transaction2))).thenReturn(transactionDto2);
         when(transactionToTransactionDtoConverter.convert(eq(transaction3))).thenReturn(transactionDto3);

        final List<TransactionDto> result = transactionDaoImpl.findLastNTransactionsForAccounts(account,payee,itemCount);

        assertEquals(3,result.size());
        assertEquals(transactionDto1,result.get(0));
        assertEquals(transactionDto2,result.get(1));
        assertEquals(transactionDto3,result.get(2));
    }

    @Test
    public void testFindLastNTransactionsForAccountsEmpty() {
        final List<Transaction> transactions = new ArrayList<>();
        when(transactionRepository.findLastNTransactionsForAccounts(eq(account),eq(payee),eq(itemCount))).thenReturn(transactions);

        final List<TransactionDto> result = transactionDaoImpl.findLastNTransactionsForAccounts(account,payee,itemCount);

        assertEquals(0,result.size());
    }


}
