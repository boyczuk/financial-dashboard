package com.boyczuk.financetracker.service;

import java.time.LocalDate;
import java.util.List;

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

        for (Transaction t : transactions) {
            total += t.amount;
            Networth nw = new Networth(total, t.date.toString());
            networthRepository.save(nw);
        }

    }
}
