package com.boyczuk.financetracker.controller.api;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boyczuk.financetracker.service.CSVImportService;
import com.boyczuk.financetracker.service.CalculateNetworth;
import com.boyczuk.financetracker.service.CalculateSpending;
import com.boyczuk.financetracker.model.ChequingTransaction;
import com.boyczuk.financetracker.model.Networth;
import com.boyczuk.financetracker.model.SavingsTransaction;
import com.boyczuk.financetracker.model.Transaction;
import com.boyczuk.financetracker.repository.NetworthRepository;
import com.boyczuk.financetracker.repository.SavingsRepository;
import com.boyczuk.financetracker.repository.ChequingRepository;

@RestController
public class CollectData {

    private final CSVImportService csvImportService;
    private final CalculateNetworth calculateNetworth;
    private final SavingsRepository savingsRepository;
    private final ChequingRepository chequingRepository;
    private final NetworthRepository networthRepository;
    private final CalculateSpending calculateSpending;

    public CollectData(CSVImportService csvImportService, SavingsRepository savingsRepository,
            CalculateSpending calculateSpending, CalculateNetworth calculateNetworth,
            ChequingRepository chequingRepository,
            NetworthRepository networthRepository) {
        this.csvImportService = csvImportService;
        this.calculateSpending = calculateSpending;
        this.savingsRepository = savingsRepository;
        this.calculateNetworth = calculateNetworth;
        this.chequingRepository = chequingRepository;
        this.networthRepository = networthRepository;
    }

    @GetMapping("/api/chequing")
    public List<ChequingTransaction> getChequing(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return chequingRepository.findByDateBetweenOrderByDateDesc(start, end);
    }

    @GetMapping("/api/savings")
    public List<SavingsTransaction> getSavings(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return savingsRepository.findByDateBetweenOrderByDateDesc(start, end);
    }

    @GetMapping("/api/networth")
    public Optional getNetworth() {
        return networthRepository.findFirstByOrderByDateDesc();
    }

    @PostMapping("/api/recalculate")
    public ResponseEntity<String> recalculate() {
        calculateNetworth.generateNetworthHistory();
        return ResponseEntity.ok("Net worth caluclated");
    }

    @GetMapping("/api/networthHistory")
    public List<Networth> getNetworthHistory(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return networthRepository.findByDateBetweenOrderByDateDesc(start, end);
    }

    @GetMapping("/api/monthlySpending")
    public HashMap<Integer, Double> getMonthlySpending() {
        return calculateSpending.thisYearMonthlySpending();
    }

}
