package com.akosszabo.demo.fp.domain.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonAutoDetect
@ToString
@AllArgsConstructor
public class TransactionFraudCheckResponse {

    private boolean flaggedForIssues;
    private List<Issue> issues;

}
