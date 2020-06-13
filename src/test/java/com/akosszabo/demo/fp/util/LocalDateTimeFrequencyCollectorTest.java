package com.akosszabo.demo.fp.util;

import com.akosszabo.demo.fp.domain.FraudCheckCode;
import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import com.akosszabo.demo.fp.service.AccountBasedFraudPreventionRule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

public class LocalDateTimeFrequencyCollectorTest {

    @Test
    public void testEmptyList() {
        List<LocalDateTime> collection = Collections.emptyList();
        final LocalDateTimeFrequencyCollector result = collection.stream().collect(LocalDateTimeFrequencyCollector.getCollector());

        assertEquals(0,result.getFrequency());
    }

    @Test
    public void testNonEmptyList() {
        final LocalDateTime now = LocalDateTime.now();
        final List<LocalDateTime> dateList = Arrays.asList(now, now.minusDays(5), now.minusDays(30), now.minusDays(35), now.minusDays(60));

        final LocalDateTimeFrequencyCollector result = dateList.stream().collect(LocalDateTimeFrequencyCollector.getCollector());

        assertEquals(15,result.getFrequency());
    }

    @Test
    public void testSameDayList() {
        final LocalDateTime now = LocalDateTime.now();
        final List<LocalDateTime> dateList = Arrays.asList(now, now, now);

        final LocalDateTimeFrequencyCollector result = dateList.stream().collect(LocalDateTimeFrequencyCollector.getCollector());

        assertEquals(0, result.getFrequency());
    }



}
