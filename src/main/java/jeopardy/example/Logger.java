package jeopardy.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Refactored Logger adhering to SOLID principles.
 * 
 * Single Responsibility: Manages event publishing and observer lifecycle.
 * Open/Closed: Delegates activity logging to ActivityLogger implementation.
 * Liskov Substitution: EventPublisher interface allows different implementations.
 * Interface Segregation: Separate interfaces for different concerns (EventPublisher, ActivityLogger, Observer).
 * Dependency Inversion: Depends on abstractions (EventPublisher, ActivityLogger, Observer).
 */
public class Logger implements EventPublisher {
    private final List<Observer> observers = new ArrayList<>();
    private final Summary summary;
    private final ActivityLogger activityLogger;
    private String caseId;
    private int correctAnswerCount = 0;
    private static final String CSV_LOG_FILE = "game_event_log.csv";

    // Singleton instance
    private static final Logger instance = new Logger();

    private Logger() {
        this.summary = new Summary();
        this.activityLogger = new GameActivityLogger(this, this.caseId);
        initializeCSVLogging();
    }

    public static Logger getInstance() {
        return instance;
    }

    @Override
    public void publish(EventRecord event) {
        // Notify all observers with the event log line
        notifyObservers(event.toLogLine());
    }

    @Override
    public void addObserver(Observer observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    private void initializeCSVLogging() {
        FileObserver csvObserver = new FileObserver(CSV_LOG_FILE);
        addObserver(csvObserver);
    }

    // Getters and setters
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseId() {
        return caseId;
    }

    public Summary getSummary() {
        return summary;
    }

    /**
     * Increment the counter for correctly answered questions.
     */
    public synchronized void incrementCorrectAnswerCount() {
        correctAnswerCount++;
    }

    /**
     * Returns the number of correctly answered questions recorded during the session.
     */
    public synchronized int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public ActivityLogger getActivityLogger() {
        return activityLogger;
    }

    // Legacy support for backward compatibility
    public void log(String message) {
        EventRecord event = new EventRecord.Builder("", message)
            .caseId(caseId)
            .build();
        publish(event);
    }

    public void logEvent(String playerId,
                         String activity,
                         String category,
                         String questionValue,
                         String answerGiven,
                         String result,
                         String scoreAfterPlay) {
        EventRecord event = new EventRecord.Builder(playerId, activity)
            .caseId(caseId)
            .category(category)
            .questionValue(questionValue)
            .answerGiven(answerGiven)
            .result(result)
            .scoreAfterPlay(scoreAfterPlay)
            .build();
        publish(event);
    }
}
