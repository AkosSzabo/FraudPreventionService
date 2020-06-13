package com.akosszabo.demo.fp.util;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class DateUtilTest {

    @Test
    public void calculateDaysBetweenLocalDateTimes() {
        assertEquals(5,DateUtil.calculateDaysBetweenLocalDateTimes(LocalDateTime.now(),LocalDateTime.now().minusDays(5)));
    }

    @Test
    public void calculateDaysBetweenLocalDateTimesReversed() {
        assertEquals(5,DateUtil.calculateDaysBetweenLocalDateTimes(LocalDateTime.now(),LocalDateTime.now().minusDays(5)));
    }
}
