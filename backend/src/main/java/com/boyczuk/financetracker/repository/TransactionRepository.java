package com.boyczuk.financetracker.repository;

import com.boyczuk.financetracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
