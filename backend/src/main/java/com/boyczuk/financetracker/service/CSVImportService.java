package com.boyczuk.financetracker.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
    private static final Map<String, Pattern> categoryPatterns = new LinkedHashMap<>();

    static {
        // Bar / Alcohol
        categoryPatterns.put("Bar/Alcohol", Pattern.compile(
                "(?i)(" +
                        "BAR\\b|BISTRO\\s*422\\s*BAR|WINE RACK|LCBO|" +
                        "BREWERY|DISTILLERY|TAVERN|PUB|LOUNGE" +
                        ")"));

        // Cafe/Coffee
        categoryPatterns.put("Cafe/coffee", Pattern.compile(
                "(?i)(" +
                        "COF+E+|CAFE|CAFÉ|ESPRESSO|LATTE|MOCHA|ROAST|" +
                        "BAKERY|BUBBLE\\s*TEA|TEASE|TEA HOUSE|" +
                        "BEAN|PIE|DARK HORSE|INSOMNIA|MOONBEAN|PERA|" +
                        "RC\\s*COFFEE|TIM HORTONS|PILOT|GREEN ROOM|BOM DIA|FILM CAFE|LARRY'S FOLLY|" +
                        "SQ \\*.*(COF+E+|TEASE|CAFE|BEAN)|LIU LOQUM|ANNEX|\\bBISTRO\\b(?!.*BAR)" + // <- BISTRO not
                                                                                                   // followed by BAR
                        ")"));
        // Groceries
        categoryPatterns.put("Groceries", Pattern.compile(
                "(?i)(" +
                        "METRO|NO FRILLS|LOBLAWS|LONGOS|FRESHCO|FOOD BASICS|WHOLE FOODS|" +
                        "SUPERMARKET|MARKET|FRESH|GROCERY" +
                        ")"));

        // Food (non-cafe)
        categoryPatterns.put("Food", Pattern.compile(
                "(?i)(" +
                        "SAM'S FOOD STORE|P\\.A\\.T\\.? CENTRAL|VILLAGE MARKET|AGP MART|" +
                        "MART|MARKET|MAC'S|7\\s*ELEVEN|CONVENIEN(C|T)|CORNER STORE|SNACKS" +
                        ")"));

        // Restaurant
        categoryPatterns.put("Restaurant", Pattern.compile(
                "(?i)(" +
                        "RESTAURANT|GRILL|STEAK|DINER|BURGER|PIZZA|SUSHI|KITCHEN|BBQ|CHICK-FIL-A|" +
                        "KING'S LANDING|YUMMY KOREAN|KENZO|SEOR AK SAN|TACO|CHIPOTLE|WINGS" +
                        ")"));

        // Clothes
        categoryPatterns.put("Clothes", Pattern.compile(
                "(?i)(" +
                        "\\b(H&M|ZARA|GAP|UNIQLO|URBAN PLANET|WINNERS|HUDSON'S BAY|OLD NAVY)\\b|" +
                        "FASHION|CLOTHING|APPAREL|OUTFITTERS|HM\\s*CA" +
                        ")"));

        // Health/Fitness
        categoryPatterns.put("Health/Fitness", Pattern.compile(
                "(?i)(" +
                        "SHOPPERS DRUG MART|PHARMACY|DRUG STORE|OPTOMETRY|CLINIC|WELLNESS|" +
                        "GYM|CHIROPRACTOR|THERAPY|HEALTH|FITNESS|OPENMAT" +
                        ")"));

        // Subscription
        categoryPatterns.put("Subscription", Pattern.compile(
                "(?i)(" +
                        "SPOTIFY|CHATGPT|OPENAI|CLOUDFLARE|NAMECHEAP|NAME-CHEAP|AMAZON.*PRIME|" +
                        "NETFLIX|DISNEY\\+|YOUTUBE PREMIUM|APPLE MUSIC|HULU|SUBSCRIPTION" +
                        ")"));

        // Transportation
        categoryPatterns.put("Transportation", Pattern.compile(
                "(?i)(" +
                        "UBER|LYFT|TTC|PRESTO|GO TRANSIT|SUBWAY|" +
                        "TRANSIT|TAP CARD|VIA RAIL|PRES/5G|PRES/5F|PRES/5B|" +
                        "AIR CANADA|WESTJET|TICKET" +
                        ")"));

        // Banking
        categoryPatterns.put("Banking", Pattern.compile(
                "(?i)(" +
                        "E[- ]*TRANSFER|INTERNET TRANSFER|ELECTRONIC FUNDS TRANSFER|" +
                        "BANKING|DEPOSIT|WITHDRAWAL|TRANSFER|REFUND" +
                        ")"));

        // Misc (fallback and junk)
        // categoryPatterns.put("Misc", Pattern.compile("(?i)(PAYPAL|SERVICE
        // CHARGE|SQUARE|GLEAM
        // HOSPI|ANNEX|DISCOUNT|REVERSAL|VARIETY|EVENTBRITE|CLOUDFLARE|UNKNOWN)"));
    }

    private final ChequingRepository chequingRepository;
    private final SavingsRepository savingsRepository;

    public CSVImportService(ChequingRepository chequingRepository, SavingsRepository savingsRepository) {
        this.chequingRepository = chequingRepository;
        this.savingsRepository = savingsRepository;
    }

    public static String checkCategory(String name) {
        for (Map.Entry<String, Pattern> entry : categoryPatterns.entrySet()) {
            if (entry.getValue().matcher(name).find()) {
                return entry.getKey();
            }
        }
        return "Miscellaneous";
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

                String category = checkCategory(name);

                if ("chequing".equalsIgnoreCase(accountName)) {
                    chequingRepository.save(new ChequingTransaction(dateString, name, amount, category));
                } else if ("savings".equalsIgnoreCase(accountName)) {
                    savingsRepository.save(new SavingsTransaction(dateString, name, amount, category));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
