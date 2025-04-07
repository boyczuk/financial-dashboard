package com.boyczuk.financetracker.repository;

import com.boyczuk.financetracker.model.Transaction;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByDateBetweenOrderByDateDesc(LocalDate start, LocalDate end);
    List<Transaction> findAllByOrderByDateDesc();
    List<Transaction> findByDateBetweenOrderByDateAsc(LocalDate start, LocalDate end);
}
