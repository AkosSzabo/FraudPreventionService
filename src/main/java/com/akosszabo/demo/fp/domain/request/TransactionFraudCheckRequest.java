package com.akosszabo.demo.fp.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
public class TransactionFraudCheckRequest {
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    @NotNull
    private String userAccountNumber;
    @NotNull
    private String destinationAccountNumber;
    @Min(0)
    private BigDecimal dollarAmount;
}
