package com.boyczuk.financetracker.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boyczuk.financetracker.model.Networth;

public interface NetworthRepository extends JpaRepository<Networth, Long> {
    List<Networth> findByDateBetweenOrderByDateDesc(LocalDate start, LocalDate end);
    List<Networth> findAllByOrderByDateDesc();
    Optional<Networth> findFirstByOrderByDateDesc();
}
