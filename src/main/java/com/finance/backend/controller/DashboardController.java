package com.finance.backend.controller;

import com.finance.backend.model.RecordType;
import com.finance.backend.repository.FinancialRecordRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final FinancialRecordRepository repository;

    public DashboardController(FinancialRecordRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/summary")
    public Map<String, BigDecimal> getSummary() {
        BigDecimal totalIncome = repository.sumAmountByType(RecordType.INCOME);
        if (totalIncome == null) totalIncome = BigDecimal.ZERO;

        BigDecimal totalExpense = repository.sumAmountByType(RecordType.EXPENSE);
        if (totalExpense == null) totalExpense = BigDecimal.ZERO;

        BigDecimal netBalance = totalIncome.subtract(totalExpense);

        Map<String, BigDecimal> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpenses", totalExpense);
        summary.put("netBalance", netBalance);

        return summary;
    }
}