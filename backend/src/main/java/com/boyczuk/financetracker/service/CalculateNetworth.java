package com.boyczuk.financetracker.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.boyczuk.financetracker.repository.NetworthRepository;
import com.boyczuk.financetracker.repository.SavingsRepository;
import com.boyczuk.financetracker.repository.ChequingRepository;
import com.boyczuk.financetracker.model.Networth;
import com.boyczuk.financetracker.model.SavingsTransaction;
import com.boyczuk.financetracker.model.ChequingTransaction;

@Service
public class CalculateNetworth {
    private final NetworthRepository networthRepository;
    private final ChequingRepository chequingRepository;
    private final SavingsRepository savingsRepository;

    public CalculateNetworth(ChequingRepository chequingRepository, SavingsRepository savingsRepository,
            NetworthRepository networthRepository) {
        this.networthRepository = networthRepository;
        this.chequingRepository = chequingRepository;
        this.savingsRepository = savingsRepository;
    }

    public void generateNetworthHistory() {
        List<ChequingTransaction> transactions = chequingRepository.findAllByOrderByDateDesc();
        List<SavingsTransaction> transactions2 = savingsRepository.findAllByOrderByDateDesc();

        double total = 0;
        Map<LocalDate, Double> netByDate = new TreeMap<>();

        for (ChequingTransaction tx : transactions) {
            netByDate.merge(tx.date, tx.amount, Double::sum);
        }

        // Aggregate savings
        for (SavingsTransaction tx : transactions2) {
            // Cool function that performs a function on existing members of the map
            netByDate.merge(tx.date, tx.amount, Double::sum);
        }

        LinkedHashMap<LocalDate, Double> dailyNetworth = new LinkedHashMap<>();

        for (Map.Entry<LocalDate, Double> entry : netByDate.entrySet()) {
            total += entry.getValue();
            dailyNetworth.put(entry.getKey(), total);
        }

        // Persist
        networthRepository.deleteAll();
        for (Map.Entry<LocalDate, Double> entry : dailyNetworth.entrySet()) {
            networthRepository.save(new Networth(entry.getValue(), entry.getKey().toString()));
        }
    }
}
