package com.akosszabo.demo.fp.converter;

import com.akosszabo.demo.fp.domain.FraudCheckResult;
import com.akosszabo.demo.fp.domain.response.Issue;
import com.akosszabo.demo.fp.domain.response.TransactionFraudCheckResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckResultsToResponseConverter implements Converter<List<FraudCheckResult>, TransactionFraudCheckResponse> {

    @Override
    public TransactionFraudCheckResponse convert(final List<FraudCheckResult> transactionFraudCheckResults) {
        final List<Issue> issues = transactionFraudCheckResults.stream().filter(r -> !r.isSuccess()).map(r -> new Issue(r.getMessage(),r.getErrorType())).collect(Collectors.toList());
        final TransactionFraudCheckResponse result = new TransactionFraudCheckResponse(issues.size() > 0, issues);
        return result;
    }
}