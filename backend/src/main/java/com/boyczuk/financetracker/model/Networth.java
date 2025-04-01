package com.boyczuk.financetracker.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Networth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public LocalDate date;
    public double amount;

    public Networth() {}

    public Networth(String dateString, double amount) {
        this.date = LocalDate.parse(dateString);
        this.amount = amount;
    }
}
