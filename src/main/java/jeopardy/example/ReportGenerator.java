package jeopardy.example;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates game summary reports in multiple formats (TXT, PDF, DOCX).
 * Adheres to Single Responsibility Principle.
 */
public class ReportGenerator {
    private final Summary summary;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ReportGenerator(Summary summary) {
        this.summary = summary;
    }

    /**
     * Generates a plain text report.
     */
    public void generateTextReport(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(buildReportContent());
        }
    }

    /**
     * Generates a report in simplified format for DOCX (can be imported).
     */
    public void generateDocxPlainText(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(buildReportContent());
        }
    }

    /**
     * Generates a report in simplified format for PDF (can be imported).
     */
    public void generatePdfPlainText(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(buildReportContent());
        }
    }

    /**
     * Builds the core report content used by all formats.
     */
    private String buildReportContent() {
        StringBuilder sb = new StringBuilder();
        
        // Header
        sb.append("================================================================================\n");
        sb.append("JEOPARDY GAME SUMMARY REPORT\n");
        sb.append("================================================================================\n");
        sb.append("Generated: ").append(LocalDateTime.now().format(dateFormatter)).append("\n\n");

        // Final Scores Summary
        sb.append("FINAL SCORES\n");
        sb.append("--------------------------------------------------------------------------------\n");
        Map<String, Integer> playerScores = calculateFinalScores();
        
        if (playerScores.isEmpty()) {
            sb.append("No game data recorded.\n");
        } else {
            playerScores.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .forEach(entry -> sb.append(String.format("%-30s %8d points\n", entry.getKey(), entry.getValue())));
        }
        
        sb.append("\n");

        // Turn-by-Turn Breakdown
        sb.append("TURN-BY-TURN BREAKDOWN\n");
        sb.append("--------------------------------------------------------------------------------\n");
        
        List<Summary.Turn> turns = summary.getTurns();
        if (turns.isEmpty()) {
            sb.append("No turns recorded.\n");
        } else {
            for (int i = 0; i < turns.size(); i++) {
                Summary.Turn turn = turns.get(i);
                sb.append(String.format("\nTurn %d:\n", i + 1));
                sb.append(String.format("  Player:        %s\n", turn.getPlayerName()));
                sb.append(String.format("  Category:      %s\n", turn.getSelectedCategory()));
                sb.append(String.format("  Question Val:  $%d\n", turn.getQuestionValue()));
                sb.append(String.format("  Question:      %s\n", formatText(turn.getQuestionText(), 60)));
                sb.append(String.format("  Given Answer:  %s\n", turn.getGivenAnswer()));
                sb.append(String.format("  Correct:       %s\n", turn.isCorrect() ? "YES" : "NO"));
                sb.append(String.format("  Points Earned: %+d\n", turn.getPointsEarned()));
                sb.append(String.format("  Running Total: %d\n", turn.getRunningTotal()));
            }
        }
        
        sb.append("\n");
        sb.append("================================================================================\n");
        
        return sb.toString();
    }

    /**
     * Formats long text with word wrapping.
     */
    private String formatText(String text, int maxWidth) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        if (text.length() <= maxWidth) {
            return text;
        }
        
        StringBuilder wrapped = new StringBuilder();
        String[] words = text.split("\\s+");
        int currentLength = 0;
        
        for (String word : words) {
            if (currentLength + word.length() + 1 > maxWidth) {
                wrapped.append("\n                    "); // Indent continuation
                currentLength = 20; // Account for indent
            }
            if (currentLength > 0) {
                wrapped.append(" ");
                currentLength++;
            }
            wrapped.append(word);
            currentLength += word.length();
        }
        
        return wrapped.toString();
    }

    /**
     * Calculates final scores for each player.
     */
    private Map<String, Integer> calculateFinalScores() {
        Map<String, Integer> scores = new HashMap<>();
        
        for (Summary.Turn turn : summary.getTurns()) {
            scores.put(turn.getPlayerName(), turn.getRunningTotal());
        }
        
        return scores;
    }

    /**
     * Generates a CSV format report for data analysis.
     */
    public void generateCSVReport(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Header
            writer.write("Turn,Player,Category,Question_Value,Question_Text,Given_Answer,Correct,Points_Earned,Running_Total\n");
            
            // Data rows
            List<Summary.Turn> turns = summary.getTurns();
            for (int i = 0; i < turns.size(); i++) {
                Summary.Turn turn = turns.get(i);
                writer.write(String.format("%d,\"%s\",\"%s\",%d,\"%s\",\"%s\",%s,%d,%d\n",
                    i + 1,
                    escapeCsv(turn.getPlayerName()),
                    escapeCsv(turn.getSelectedCategory()),
                    turn.getQuestionValue(),
                    escapeCsv(turn.getQuestionText()),
                    escapeCsv(turn.getGivenAnswer()),
                    turn.isCorrect() ? "TRUE" : "FALSE",
                    turn.getPointsEarned(),
                    turn.getRunningTotal()
                ));
            }
        }
    }

    /**
     * Escapes special characters for CSV format.
     */
    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\"", "\"\"");
    }
}
