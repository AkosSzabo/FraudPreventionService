package com.akosszabo.demo.fp.service;

import com.akosszabo.demo.fp.domain.FraudCheckType;
import com.akosszabo.demo.fp.domain.TransactionContext;
import com.akosszabo.demo.fp.domain.FraudCheckResult;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class FrequencyBasedFraudPreventionRule implements FraudPreventionRule {
    public static final String FAILURE_MESSAGE = "The transaction date is outside of the expected range";

    @Override
    public FraudCheckResult evaluate(final TransactionContext transactionContext) {

   //     long noOfDaysBetween = ChronoUnit.DAYS.between(now, now);
        FraudCheckResult result = FraudCheckResult.createSuccessful();
        if(transactionContext.getTransactionHistory().size()>1) {
            final ArrayList<Integer> daysBetweenTransactions = new ArrayList<>();
            final LocalDateTime previousDate = null;
            Long sum = 0L;
            ////////////////////////////////
//            final List<LD> collect = transactionContext.getTransactionHistory().stream().map(transactionDto -> transactionContext.getDateTime()).sorted().map(transactionDto -> new LD(transactionContext.getDateTime(), 0, 0L)).collect(Collectors.toList());
//            collect.stream().reduce((a,b) -> {
//                if(a!=null) {
//
//                }
//
//                return new LD(b);
//            });
            /////////////////
                for(Integer i=1;i<transactionContext.getTransactionHistory().size();i++) {
                    sum += ChronoUnit.DAYS.between( transactionContext.getTransactionHistory().get(i).getTransactionDate(),transactionContext.getTransactionHistory().get(i-1).getTransactionDate());
                }
            final long avarageFequency = sum / (transactionContext.getTransactionHistory().size() - 1);
            final long maxFrequency = avarageFequency*2;
            final long minFrequency = avarageFequency/2;
            final long daysPassed = ChronoUnit.DAYS.between(transactionContext.getTransactionHistory().get(0).getTransactionDate(),transactionContext.getDateTime());
            if(maxFrequency<daysPassed||daysPassed<minFrequency) {
                result = FraudCheckResult.createFailed(FAILURE_MESSAGE, FraudCheckType.FREQUENCY);
            }


        }
        return result;
    }
    @Data
    @AllArgsConstructor
    class LD {
        LocalDateTime ldt;
        int count;
        long sum;
    }

}
