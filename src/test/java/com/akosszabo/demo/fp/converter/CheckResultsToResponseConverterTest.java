package com.akosszabo.demo.fp.converter;

import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.FraudCheckCode;
import com.akosszabo.demo.fp.domain.response.TransactionFraudCheckResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CheckResultsToResponseConverterTest {

    public static final String ERROR_MESSAGE = "Message2";

    private CheckResultsToResponseConverter converter = new CheckResultsToResponseConverter();

    @Test
    public void testFailureResponseConversion() {
        final List<FraudCheckResult> resultList = new ArrayList<>();
        final FraudCheckResult checkResult1 = FraudCheckResult.createSuccessful();
        final FraudCheckResult checkResult2 = FraudCheckResult.createFailed(ERROR_MESSAGE, FraudCheckCode.AMOUNT);
        resultList.add(checkResult1);
        resultList.add(checkResult2);

        final TransactionFraudCheckResponse result = converter.convert(resultList);

        assertEquals(1,result.getIssues().size());
        assertEquals(ERROR_MESSAGE,result.getIssues().get(0).getMessage());
        assertTrue(result.isFlaggedForIssues());
    }

    @Test
    public void tesSuccessResponseConversion() {
        final List<FraudCheckResult> resultList = new ArrayList<>();
        final FraudCheckResult checkResult1 = FraudCheckResult.createSuccessful();
        resultList.add(checkResult1);

        final TransactionFraudCheckResponse result = converter.convert(resultList);

        assertEquals(0,result.getIssues().size());
        assertFalse(result.isFlaggedForIssues());
    }

}
