package jeopardy.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileObserver implements Observer {
    private final Path logPath;

    public FileObserver(String fileName) {
        this.logPath = Paths.get(fileName);
        // Write header if file does not exist
        if (!Files.exists(this.logPath)) {
            String header = "Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play" + System.lineSeparator();
            try {
                Files.write(this.logPath, header.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.err.println("Failed to write header to log file: " + e.getMessage());
            }
        }
    }

    @Override
    public void update(String message) {
        // Convert space-separated message from Logger to CSV format
        String csvLine = convertToCSV(message);
        String line = csvLine + System.lineSeparator();
        try {
            Files.write(logPath, line.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }

    private String convertToCSV(String message) {
        // Expected number of CSV columns (matches header)
        final int expectedFields = 9;
        // Guard against null input
        if (message == null) {
            return String.join(",", java.util.Collections.nCopies(expectedFields, ""));
        }

        // Split by two-or-more whitespace characters (Logger uses double-spaces)
        String[] fields = message.split("\\s{2,}");

        // Normalize length: pad with empty strings if fewer fields than expected
        String[] normalized = new String[expectedFields];
        for (int i = 0; i < expectedFields; i++) {
            if (i < fields.length) {
                normalized[i] = fields[i].trim();
            } else {
                normalized[i] = "";
            }
        }

        StringBuilder csv = new StringBuilder();
        for (int i = 0; i < normalized.length; i++) {
            String field = normalized[i];
            // Escape quotes by doubling them and wrap in quotes if field contains comma or quote
            if (field.contains(",") || field.contains("\"")) {
                field = "\"" + field.replace("\"", "\"\"") + "\"";
            }
            csv.append(field);
            if (i < normalized.length - 1) {
                csv.append(',');
            }
        }
        return csv.toString();
    }
}
