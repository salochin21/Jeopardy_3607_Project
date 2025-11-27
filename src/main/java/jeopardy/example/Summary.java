package jeopardy.example;

import java.util.ArrayList;
import java.util.List;

public class Summary {
    
    /**
     * Represents a single turn in the game with all relevant details.
     */
    public static class Turn {
        private final String playerName;
        private final String selectedCategory;
        private final int questionValue;
        private final String questionText;
        private final String givenAnswer;
        private final boolean isCorrect;
        private final int pointsEarned;
        private final int runningTotal;
        
        public Turn(String playerName, String selectedCategory, int questionValue, 
                    String questionText, String givenAnswer, boolean isCorrect, 
                    int pointsEarned, int runningTotal) {
            this.playerName = playerName;
            this.selectedCategory = selectedCategory;
            this.questionValue = questionValue;
            this.questionText = questionText;
            this.givenAnswer = givenAnswer;
            this.isCorrect = isCorrect;
            this.pointsEarned = pointsEarned;
            this.runningTotal = runningTotal;
        }
        
        // Getters
        public String getPlayerName() {
            return playerName;
        }
        
        public String getSelectedCategory() {
            return selectedCategory;
        }
        
        public int getQuestionValue() {
            return questionValue;
        }
        
        public String getQuestionText() {
            return questionText;
        }
        
        public String getGivenAnswer() {
            return givenAnswer;
        }
        
        public boolean isCorrect() {
            return isCorrect;
        }
        
        public int getPointsEarned() {
            return pointsEarned;
        }
        
        public int getRunningTotal() {
            return runningTotal;
        }
        
        @Override
        public String toString() {
            return String.format(
                "Player: %s | Category: %s | Value: %d | Question: %s | Answer: %s | Correct: %s | Points: %d | Total: %d",
                playerName, selectedCategory, questionValue, questionText, givenAnswer, 
                isCorrect ? "YES" : "NO", pointsEarned, runningTotal
            );
        }
    }
    
    private final List<Turn> turns;
    
    public Summary() {
        this.turns = new ArrayList<>();
    }
    
    /**
     * Records a turn in the game summary.
     */
    public void recordTurn(String playerName, String selectedCategory, int questionValue,
                          String questionText, String givenAnswer, boolean isCorrect,
                          int pointsEarned, int runningTotal) {
        Turn turn = new Turn(playerName, selectedCategory, questionValue, questionText, 
                            givenAnswer, isCorrect, pointsEarned, runningTotal);
        turns.add(turn);
    }
    
    /**
     * Returns all turns recorded in this summary.
     */
    public List<Turn> getTurns() {
        return new ArrayList<>(turns);
    }
    
    /**
     * Returns the number of turns recorded.
     */
    public int getTurnCount() {
        return turns.size();
    }
    
    /**
     * Displays the full turn-by-turn breakdown.
     */
    public void displaySummary() {
        System.out.println("\n========== GAME SUMMARY ==========");
        if (turns.isEmpty()) {
            System.out.println("No turns recorded yet.");
        } else {
            for (int i = 0; i < turns.size(); i++) {
                System.out.printf("Turn %d: %s%n", i + 1, turns.get(i));
            }
        }
        System.out.println("=================================\n");
    }
    
    /**
     * Returns a formatted string of the entire summary for logging or export.
     */
    public String getSummaryAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== GAME SUMMARY ==========\n");
        for (int i = 0; i < turns.size(); i++) {
            sb.append(String.format("Turn %d: %s%n", i + 1, turns.get(i)));
        }
        sb.append("=================================\n");
        return sb.toString();
    }
}
