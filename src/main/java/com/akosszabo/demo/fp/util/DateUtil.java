package com.akosszabo.demo.fp.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static int calculateDaysBetweenLocalDateTimes(final LocalDateTime date1, final LocalDateTime date2) {
        LocalDateTime date1End = date1.toLocalDate().atTime(LocalTime.MAX);
        LocalDateTime date2End = date2.toLocalDate().atTime(LocalTime.MAX);
        return (int)Math.abs(ChronoUnit.DAYS.between(date1End,date2End));
    }
}
