package jeopardy.example;

/**
 * Interface for activity-specific event logging.
 * Adheres to Dependency Inversion principle - depend on abstractions.
 */
public interface ActivityLogger {
    void logStartGame(String playerId);
    void logLoadFile(String playerId, String fileName);
    void logFileLoadedSuccessfully(String playerId, String fileName);
    void logSelectPlayerCount(String playerId, String playerCount);
    void logEnterPlayerName(String playerId, String playerName);
    void logSelectCategory(String playerId, String category);
    void logSelectQuestion(String playerId, String category, String questionValue);
    void logAnswerQuestion(String playerId, String category, String questionValue, String answer);
    void logScoreUpdated(String playerId, String score);
    void logGenerateReport(String playerId);
    void logGenerateEventLog(String playerId);
    void logExitGame(String playerId);
}
