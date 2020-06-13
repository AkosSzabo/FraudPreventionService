package com.akosszabo.demo.fp.domain.response;

import com.akosszabo.demo.fp.domain.FraudCheckCode;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@JsonAutoDetect
@Getter
@ToString
public class Issue {
    private String message;
    private FraudCheckCode code;
}
