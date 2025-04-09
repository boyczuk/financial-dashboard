package com.boyczuk.financetracker.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.data.repository.query.CachingValueExpressionDelegate;
import org.springframework.stereotype.Service;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.boyczuk.financetracker.model.ChequingTransaction;
import com.boyczuk.financetracker.model.SavingsTransaction;
import com.boyczuk.financetracker.model.Transaction;
import com.boyczuk.financetracker.repository.ChequingRepository;
import com.boyczuk.financetracker.repository.SavingsRepository;

@Service
public class CSVImportService {

    private final ChequingRepository chequingRepository;
    private final SavingsRepository savingsRepository;

    public CSVImportService(ChequingRepository chequingRepository, SavingsRepository savingsRepository) {
        this.chequingRepository = chequingRepository;
        this.savingsRepository = savingsRepository;
    }

    public void saveToDB(InputStream inputStream, String accountName) {
        if ("chequing".equalsIgnoreCase(accountName)) {
            chequingRepository.deleteAll();
        } else if ("savings".equalsIgnoreCase(accountName)) {
            savingsRepository.deleteAll();
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] parts;

            while ((parts = reader.readNext()) != null) {
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
                    System.out.println("Skipping row: " + String.join(",", parts));
                    continue;
                }

                if ("chequing".equalsIgnoreCase(accountName)) {
                    chequingRepository.save(new ChequingTransaction(dateString, name, amount));
                } else if ("savings".equalsIgnoreCase(accountName)) {
                    savingsRepository.save(new SavingsTransaction(dateString, name, amount));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
