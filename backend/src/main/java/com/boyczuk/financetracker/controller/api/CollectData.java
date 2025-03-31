package com.boyczuk.financetracker.controller.api;

import java.io.InputStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boyczuk.financetracker.service.CSVImportService;

@RestController
public class CollectData {

    private final CSVImportService csvImportService;

    public CollectData(CSVImportService csvImportService) {
        this.csvImportService = csvImportService;
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
}
