package com.akosszabo.demo.fp.converter;

import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.response.TransactionFraudCheckResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CheckResultsToResponseConverterTest {

    public static final String MESSAGE_1 = "Message1";
    public static final String MESSAGE_2 = "Message2";

    private CheckResultsToResponseConverter converter = new CheckResultsToResponseConverter();

    @Test
    public void testFailureResponseConversion() {
        final List<FraudCheckResult> resultList = new ArrayList<>();
        final FraudCheckResult checkResult1 = new FraudCheckResult(true, MESSAGE_1);
        final FraudCheckResult checkResult2 = new FraudCheckResult(false, MESSAGE_2);
        resultList.add(checkResult1);
        resultList.add(checkResult2);

        final TransactionFraudCheckResponse result = converter.convert(resultList);

        assertEquals(1,result.getIssues().size());
        assertEquals(MESSAGE_2,result.getIssues().get(0));
        assertTrue(result.isFlaggedForFraud());
    }

    @Test
    public void tesSuccessResponseConversion() {
        final List<FraudCheckResult> resultList = new ArrayList<>();
        final FraudCheckResult checkResult1 = new FraudCheckResult(true, MESSAGE_1);
        resultList.add(checkResult1);

        final TransactionFraudCheckResponse result = converter.convert(resultList);

        assertEquals(0,result.getIssues().size());
        assertFalse(result.isFlaggedForFraud());
    }

}
