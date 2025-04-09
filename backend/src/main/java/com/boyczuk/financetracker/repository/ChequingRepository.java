package com.boyczuk.financetracker.repository;

import com.boyczuk.financetracker.model.ChequingTransaction;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;;

public interface ChequingRepository extends JpaRepository<ChequingTransaction, Long> {
    List<ChequingTransaction> findByDateBetweenOrderByDateDesc(LocalDate start, LocalDate end);
    List<ChequingTransaction> findAllByOrderByDateDesc();
    List<ChequingTransaction> findByDateBetweenOrderByDateAsc(LocalDate start, LocalDate end);
}
