package com.akosszabo.demo.fp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FraudCheckResult {

    public static FraudCheckResult createSuccessful() {
        return new FraudCheckResult(true,"", null);
    }

    public static FraudCheckResult createFailed(final String message, final FraudCheckType errorType) {
        return new FraudCheckResult(false,message, errorType);
    }

    private boolean success;
    private String message;
    private FraudCheckType errorType;
}
