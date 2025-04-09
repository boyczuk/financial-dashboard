package com.boyczuk.financetracker.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "savings_transactions")
public class SavingsTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public LocalDate date;
    public String name;
    public double amount;

    public SavingsTransaction() {}

    public SavingsTransaction(String dateString, String name, double amount) {
        this.date = LocalDate.parse(dateString);;
        this.name = name;
        this.amount = amount;
    }
}
