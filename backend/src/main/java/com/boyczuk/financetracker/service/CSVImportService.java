package com.boyczuk.financetracker.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.springframework.stereotype.Service;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.boyczuk.financetracker.model.Transaction;
import com.boyczuk.financetracker.repository.TransactionRepository;


@Service
public class CSVImportService {

    private final TransactionRepository transactionRepository;

    public CSVImportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public InputStream importCSV() {
        return getClass().getClassLoader().getResourceAsStream("cibc2.csv");
    }

    public void saveToDB(InputStream inputStream) {

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] parts;

            while ((parts = reader.readNext()) != null) {
                // skip empty or malformed rows
                if (parts.length < 3)
                    continue;

                String dateString = parts[0];
                String name = parts[1];
                double amount;

                try {
                    if (parts.length == 4 && !parts[3].isBlank()) {
                        amount = Double.parseDouble(parts[3]);
                    } else if (!parts[2].isBlank()) {
                        amount = -1 * Double.parseDouble(parts[2]);
                    } else {
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipping row due to invalid amount: " + String.join(",", parts));
                    continue;
                }

                Transaction transaction = new Transaction(dateString, name, amount);
                transactionRepository.save(transaction);
                System.out.println(transaction.name + ": " + transaction.amount);


            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

    }
}
