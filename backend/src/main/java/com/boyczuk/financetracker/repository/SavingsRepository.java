package com.boyczuk.financetracker.repository;

import com.boyczuk.financetracker.model.SavingsTransaction;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;;

public interface SavingsRepository extends JpaRepository<SavingsTransaction, Long> {
    List<SavingsTransaction> findByDateBetweenOrderByDateDesc(LocalDate start, LocalDate end);
    List<SavingsTransaction> findAllByOrderByDateDesc();
    List<SavingsTransaction> findByDateBetweenOrderByDateAsc(LocalDate start, LocalDate end);
}
