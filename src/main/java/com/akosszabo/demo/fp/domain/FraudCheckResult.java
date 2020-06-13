package com.akosszabo.demo.fp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FraudCheckResult {

    private boolean success;
    private String message;
    private FraudCheckCode errorCode;

    public static FraudCheckResult createSuccessful() {
        return new FraudCheckResult(true,"", null);
    }

    public static FraudCheckResult createFailed(final String message, final FraudCheckCode errorType) {
        return new FraudCheckResult(false,message, errorType);
    }

}
