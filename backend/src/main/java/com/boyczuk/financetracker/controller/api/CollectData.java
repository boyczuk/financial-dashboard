package com.boyczuk.financetracker.controller.api;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boyczuk.financetracker.service.CSVImportService;
import com.boyczuk.financetracker.model.Transaction;
import com.boyczuk.financetracker.repository.TransactionRepository;

@RestController
public class CollectData {

    private final CSVImportService csvImportService;
    private final TransactionRepository transactionRepository;

    public CollectData(CSVImportService csvImportService, TransactionRepository transactionRepository) {
        this.csvImportService = csvImportService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/api/collect")
    public String getData() {
        return "Gathering csv file";
    }

    @PostMapping("/api/store")
    public String putData() {
        return "Putting data in PSQL DB";
    }

    @GetMapping("/api/import")
    public ResponseEntity<String> getImportCSV() {
        InputStream inputStream = csvImportService.importCSV();
        csvImportService.saveToDB(inputStream);
        return ResponseEntity.ok("Import triggered");
    }

    @GetMapping("/api/transactions")
    public List<Transaction> getTransactionsInRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
                return transactionRepository.findByDateBetween(start, end);
    }
}
