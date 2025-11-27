package jeopardy.example;

/**
 * Implementation of ActivityLogger for game-specific activities.
 * Adheres to Open/Closed Principle - can be extended for new activity types.
 */
public class GameActivityLogger implements ActivityLogger {
    private final EventPublisher eventPublisher;
    private final String caseId;

    public GameActivityLogger(EventPublisher eventPublisher, String caseId) {
        this.eventPublisher = eventPublisher;
        this.caseId = caseId;
    }

    @Override
    public void logStartGame(String playerId) {
        publishEvent(new EventRecord.Builder(playerId, "Start Game")
            .caseId(caseId)
            .build());
    }

    @Override
    public void logLoadFile(String playerId, String fileName) {
        publishEvent(new EventRecord.Builder(playerId, "Load File")
            .caseId(caseId)
            .answerGiven(fileName)
            .build());
    }

    @Override
    public void logFileLoadedSuccessfully(String playerId, String fileName) {
        publishEvent(new EventRecord.Builder(playerId, "File Loaded Successfully")
            .caseId(caseId)
            .answerGiven(fileName)
            .build());
    }

    @Override
    public void logSelectPlayerCount(String playerId, String playerCount) {
        publishEvent(new EventRecord.Builder(playerId, "Select Player Count")
            .caseId(caseId)
            .answerGiven(playerCount)
            .build());
    }

    @Override
    public void logEnterPlayerName(String playerId, String playerName) {
        publishEvent(new EventRecord.Builder(playerId, "Enter Player Name")
            .caseId(caseId)
            .answerGiven(playerName)
            .build());
    }

    @Override
    public void logSelectCategory(String playerId, String category) {
        publishEvent(new EventRecord.Builder(playerId, "Select Category")
            .caseId(caseId)
            .category(category)
            .build());
    }

    @Override
    public void logSelectQuestion(String playerId, String category, String questionValue) {
        publishEvent(new EventRecord.Builder(playerId, "Select Question")
            .caseId(caseId)
            .category(category)
            .questionValue(questionValue)
            .build());
    }

    @Override
    public void logAnswerQuestion(String playerId, String category, String questionValue, String answer) {
        publishEvent(new EventRecord.Builder(playerId, "Answer Question")
            .caseId(caseId)
            .category(category)
            .questionValue(questionValue)
            .answerGiven(answer)
            .build());
    }

    @Override
    public void logScoreUpdated(String playerId, String score) {
        publishEvent(new EventRecord.Builder(playerId, "Score Updated")
            .caseId(caseId)
            .scoreAfterPlay(score)
            .build());
    }

    @Override
    public void logGenerateReport(String playerId) {
        publishEvent(new EventRecord.Builder(playerId, "Generate Report")
            .caseId(caseId)
            .build());
    }

    @Override
    public void logGenerateEventLog(String playerId) {
        publishEvent(new EventRecord.Builder(playerId, "Generate Event Log")
            .caseId(caseId)
            .build());
    }

    @Override
    public void logExitGame(String playerId) {
        publishEvent(new EventRecord.Builder(playerId, "Exit Game")
            .caseId(caseId)
            .build());
    }

    private void publishEvent(EventRecord event) {
        eventPublisher.publish(event);
    }
}
