package com.akosszabo.demo.fp.util;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class LocalDateTimeFrequencyCollector implements Consumer<LocalDateTime> {

    private int count = 0;
    private int sumDays = 0;
    private LocalDateTime lastDate;

    public static Collector<LocalDateTime, ?, LocalDateTimeFrequencyCollector> getCollector() {
        return Collector.of(LocalDateTimeFrequencyCollector::new, LocalDateTimeFrequencyCollector::accept,
                LocalDateTimeFrequencyCollector::merge);
    }

    public void accept(LocalDateTime other) {
        if (count != 0) {
            sumDays += DateUtil.calculateDaysBetweenLocalDateTimes(other, lastDate);
        }
        count++;
        lastDate = other;
    }

    public LocalDateTimeFrequencyCollector merge(LocalDateTimeFrequencyCollector s) {
        sumDays += s.sumDays;
        count += s.count;
        return this;
    }

    public int getFrequency() {
        int result = 0;
        if (count > 1) {
            result = sumDays / (count - 1);
        }
        return result;
    }


}
