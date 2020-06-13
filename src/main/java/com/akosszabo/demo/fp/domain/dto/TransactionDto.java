package com.akosszabo.demo.fp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDto {

        private Long id;
        private LocalDateTime transactionDate;
        private String sourceAccount;
        private String targetAccount;
        private BigDecimal dollarAmount;

}
