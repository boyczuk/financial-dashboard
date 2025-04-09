package com.boyczuk.financetracker.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.boyczuk.financetracker.repository.NetworthRepository;
import com.boyczuk.financetracker.repository.ChequingRepository;
import com.boyczuk.financetracker.model.Networth;
import com.boyczuk.financetracker.model.ChequingTransaction;

@Service
public class CalculateNetworth {
    private final NetworthRepository networthRepository;
    private final ChequingRepository chequingRepository;

    public CalculateNetworth(ChequingRepository chequingRepository, NetworthRepository networthRepository) {
        this.networthRepository = networthRepository;
        this.chequingRepository = chequingRepository;
    }

    // public void generateNetworthHistory() {
    //     List<ChequingTransaction> transactions = chequingRepository.findAllByOrderByDateDesc();

    //     double total = 0;
    //     Map<LocalDate, Double> dailyNetworth = new LinkedHashMap<>();

    //     for (ChequingTransaction t : transactions) {
    //         total += t.amount;
    //         dailyNetworth.put(t.date, total);
    //     }

    //     networthRepository.deleteAll();

    //     for (Map.Entry<LocalDate, Double> entry : dailyNetworth.entrySet()) {
    //         Networth nw = new Networth(entry.getValue(), entry.getKey().toString());
    //         networthRepository.save(nw);
    //     }

    // }
}
