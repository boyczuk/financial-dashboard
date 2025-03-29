package com.boyczuk.financetracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public LocalDate date;
    public String name;
    public double amount;

    public Transaction(String dateString, String name, double amount) {
        this.date = LocalDate.parse(dateString);
        this.name = name;
        this.amount = amount;
    }


}
