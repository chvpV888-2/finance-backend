package com.finance.backend.controller;

import com.finance.backend.model.FinancialRecord;
import com.finance.backend.model.RecordType;
import com.finance.backend.repository.FinancialRecordRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final FinancialRecordRepository repository;

    public RecordController(FinancialRecordRepository repository) {
        this.repository = repository;
    }

    // GET: Fetch records (NOW WITH FILTERING!)
    // Example: /api/records?type=INCOME
    @GetMapping
    public List<FinancialRecord> getAllRecords(@RequestParam(required = false) RecordType type) {
        if (type != null) {
            return repository.findByType(type); // Filtered list
        }
        return repository.findAll(); // All records
    }

    // POST: Create a new record
    @PostMapping
    public FinancialRecord createRecord(@Valid @RequestBody FinancialRecord record) {
        return repository.save(record);
    }

    // PUT: Update an existing record (NEW!)
    @PutMapping("/{id}")
    public ResponseEntity<FinancialRecord> updateRecord(@PathVariable Long id, @Valid @RequestBody FinancialRecord updatedData) {
        return repository.findById(id).map(existingRecord -> {
            existingRecord.setAmount(updatedData.getAmount());
            existingRecord.setType(updatedData.getType());
            existingRecord.setCategory(updatedData.getCategory());
            existingRecord.setDate(updatedData.getDate());
            existingRecord.setNotes(updatedData.getNotes());
            return ResponseEntity.ok(repository.save(existingRecord));
        }).orElse(ResponseEntity.notFound().build()); // Returns 404 if ID doesn't exist
    }

    // DELETE: Remove a record by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}