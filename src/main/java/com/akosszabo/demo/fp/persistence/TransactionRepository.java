package com.akosszabo.demo.fp.persistence;

import com.akosszabo.demo.fp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query( value = "SELECT * FROM transactions t where t.source_account = :source" +
    " and t.target_account = :target order by t.transaction_date DESC limit :itemCount",
            nativeQuery = true)
    List<Transaction> findLastNTransactionsForAccounts(@Param("source") final String sourceAccount,
                                            @Param("target") final String targetAccount,
                                            @Param("itemCount") final Integer itemCount);
    }
