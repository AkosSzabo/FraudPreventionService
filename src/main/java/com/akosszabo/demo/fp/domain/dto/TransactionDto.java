package com.akosszabo.demo.fp.domain.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class TransactionDto {
        private Long id;
        private LocalDateTime transactionDate;
        private String sourceAccount;
        private String targetAccount;
        private BigDecimal dollarAmount;
}
