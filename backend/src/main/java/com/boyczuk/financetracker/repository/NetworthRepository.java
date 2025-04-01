package com.boyczuk.financetracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boyczuk.financetracker.model.Networth;

public interface NetworthRepository extends JpaRepository<Networth, Long> {
    List<Networth> findByDateBetween(LocalDate start, LocalDate end);
    
}
