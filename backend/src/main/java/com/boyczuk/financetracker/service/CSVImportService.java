package com.boyczuk.financetracker.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.springframework.stereotype.Service;

import com.boyczuk.financetracker.model.Transaction;
import com.boyczuk.financetracker.repository.TransactionRepository;

@Service
public class CSVImportService {

    private final TransactionRepository transactionRepository;

    public CSVImportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public InputStream importCSV() {
        return getClass().getClassLoader().getResourceAsStream("cibc.csv");
    }

    public void saveToDB(InputStream inputStream) {        
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String dateString = parts[0];
                String name = parts[1];
                double amount;
                
                if (parts.length == 4) {
                    amount = -1 * Double.parseDouble(parts[3]);
                } else {
                    amount = Double.parseDouble(parts[2]);
                }
                
                Transaction transaction = new Transaction(dateString, name, amount);
                transactionRepository.save(transaction);
                System.out.println(transaction.name + ": " + transaction.amount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            
    }
}
