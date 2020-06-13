package com.akosszabo.demo.fp.domain;

import com.akosszabo.demo.fp.domain.dto.TransactionDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TransactionContext {
    private LocalDateTime dateTime;
    private String userAccountNumber;
    private String payeeAccountNumber;
    private BigDecimal dollarAmount;
    private List<TransactionDto> transactionHistory;
}
