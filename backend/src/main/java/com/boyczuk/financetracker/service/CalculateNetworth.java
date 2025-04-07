package com.boyczuk.financetracker.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.boyczuk.financetracker.repository.NetworthRepository;
import com.boyczuk.financetracker.repository.TransactionRepository;
import com.boyczuk.financetracker.model.Networth;
import com.boyczuk.financetracker.model.Transaction;

@Service
public class CalculateNetworth {
    private final NetworthRepository networthRepository;
    private final TransactionRepository transactionRepository;

    public CalculateNetworth(TransactionRepository transactionRepository, NetworthRepository networthRepository) {
        this.networthRepository = networthRepository;
        this.transactionRepository = transactionRepository;
    }

    public void generateNetworthHistory() {
        List<Transaction> transactions = transactionRepository.findAllByOrderByDateDesc();

        double total = 0;
        Map<LocalDate, Double> dailyNetworth = new LinkedHashMap<>();

        for (Transaction t : transactions) {
            total += t.amount;
            dailyNetworth.put(t.date, total);
        }

        networthRepository.deleteAll();

        for (Map.Entry<LocalDate, Double> entry : dailyNetworth.entrySet()) {
            Networth nw = new Networth(entry.getValue(), entry.getKey().toString());
            networthRepository.save(nw);
        }

    }
}
