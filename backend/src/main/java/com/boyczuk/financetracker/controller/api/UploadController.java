package com.boyczuk.financetracker.controller.api;

import java.io.IOException;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boyczuk.financetracker.service.CSVImportService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UploadController {
    private final CSVImportService csvImportService;

    public UploadController(CSVImportService csvImportService) {
        this.csvImportService = csvImportService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("account") String accountName) {
        System.out.println("Received upload for account: " + accountName);
        try {
            csvImportService.saveToDB(file.getInputStream(), accountName);
            return ResponseEntity.ok("File processed properly.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("failed to process file.");
        }
    }
}
