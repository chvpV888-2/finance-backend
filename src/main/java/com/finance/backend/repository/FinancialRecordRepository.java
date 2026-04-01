package com.finance.backend.repository;

import com.finance.backend.model.FinancialRecord;
import com.finance.backend.model.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List; // <--- This is the magic line we were missing!

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    // Spring Boot writes the SQL automatically to filter by type!
    List<FinancialRecord> findByType(RecordType type);

    // Custom query to calculate total money for Income or Expense
    @Query("SELECT SUM(f.amount) FROM FinancialRecord f WHERE f.type = :type")
    BigDecimal sumAmountByType(RecordType type);
}