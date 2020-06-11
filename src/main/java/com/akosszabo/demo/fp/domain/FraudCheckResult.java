package com.akosszabo.demo.fp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FraudCheckResult {

    public static FraudCheckResult createSuccessful() {
        return new FraudCheckResult(true,"");
    }

    public static FraudCheckResult createFailed(final String message) {
        return new FraudCheckResult(false,message);
    }

    private boolean success;
    private String message;
}
