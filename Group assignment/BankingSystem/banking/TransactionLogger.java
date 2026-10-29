package banking;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//final utility class used to record banking transactions in transactions.txt
public final class TransactionLogger {

    private static final String FILE_NAME = "transactions.txt";

    public static void logTransaction(
            String accountNumber,
            String transactionType,
            double amount,
            double balance,
            String result) {

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String dateTime = now.format(formatter);

        String record = "Date/Time: " + dateTime
                + " | Account: " + accountNumber
                + " | Type: " + transactionType
                + " | Amount: M" + amount
                + " | Balance: M" + balance
                + " | Result: " + result
                + System.lineSeparator();

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {

            writer.write(record);

        } catch (IOException e) {

            System.out.println("Error writing transaction to file.");
        }
    }
}