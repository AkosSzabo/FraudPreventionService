package com.akosszabo.demo.fp.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @Column( name = "id")
    private Long id;

    @Column( name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column( name = "source_account")
    private String sourceAccount;

    @Column( name = "target_account")
    private String targetAccount;

    @Column( name = "dollar_amount")
    private BigDecimal dollarAmount;

}
