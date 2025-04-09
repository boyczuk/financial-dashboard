package com.boyczuk.financetracker.service;

import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.boyczuk.financetracker.model.ChequingTransaction;
import com.boyczuk.financetracker.repository.ChequingRepository;

@Service
public class CalculateSpending {

    private final ChequingRepository chequingRepository;

    public CalculateSpending(ChequingRepository chequingRepository) {
        this.chequingRepository = chequingRepository;
    }

    public HashMap<Integer, Double> thisYearMonthlySpending() {
        LocalDate date = LocalDate.now();
        LocalDate yearAgo = date.minusYears(1);

        List<ChequingTransaction> transactions = chequingRepository.findByDateBetweenOrderByDateDesc(yearAgo, date);
        HashMap<Integer, Double> monthlyTotals = new HashMap<>();

        monthlyTotals.put(1, 0.0);
        monthlyTotals.put(2, 0.0);
        monthlyTotals.put(3, 0.0);
        monthlyTotals.put(4, 0.0);
        monthlyTotals.put(5, 0.0);
        monthlyTotals.put(6, 0.0);
        monthlyTotals.put(7, 0.0);
        monthlyTotals.put(8, 0.0);
        monthlyTotals.put(9, 0.0);
        monthlyTotals.put(10, 0.0);
        monthlyTotals.put(11, 0.0);
        monthlyTotals.put(12, 0.0);

        for (ChequingTransaction transaction : transactions) {
            if (transaction.amount < 0) {
                int month = transaction.date.getMonthValue();
                double currMonthVal = monthlyTotals.get(month) + Math.abs(transaction.amount);
                monthlyTotals.put(month, currMonthVal);
            }
        }

        return monthlyTotals;
    }
}
