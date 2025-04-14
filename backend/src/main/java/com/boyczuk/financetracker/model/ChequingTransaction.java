package com.boyczuk.financetracker.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "chequing_transactions")
public class ChequingTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public LocalDate date;
    public String name;
    public double amount;
    public String category;

    public ChequingTransaction() {}

    public ChequingTransaction(String dateString, String name, double amount, String category) {
        this.date = LocalDate.parse(dateString);;
        this.name = name;
        this.amount = amount;
        this.category = category;
    }
}
