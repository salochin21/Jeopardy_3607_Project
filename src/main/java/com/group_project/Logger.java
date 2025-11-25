package com.group_project;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Logger {
    private List<Observer> observers = new ArrayList<>();

    // Simple singleton so components can access a central logger
    private static Logger instance = new Logger();
    private String caseId;

    private Logger() {}

    public static Logger getInstance() {
        return instance;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void addObserver(Observer observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void log(String message) {
        // Backwards compatible simple log (will include a timestamp and empty structured fields)
        String timestamp = Instant.now().toString();
        String line = String.join("  ", Arrays.asList(caseId != null ? caseId : "", "", message, timestamp, "", "", "", "", ""));
        //System.out.println("LOG: " + line);
        notifyObservers(line);
    }

    public void logEvent(String playerId,
                         String activity,
                         String category,
                         String questionValue,
                         String answerGiven,
                         String result,
                         String scoreAfterPlay) {
        String timestamp = Instant.now().toString();
        String line = String.join("  ", Arrays.asList(
                caseId != null ? caseId : "",
                playerId != null ? playerId : "",
                activity != null ? activity : "",
                timestamp,
                category != null ? category : "",
                questionValue != null ? questionValue : "",
                answerGiven != null ? answerGiven : "",
                result != null ? result : "",
                scoreAfterPlay != null ? scoreAfterPlay : ""
        ));
        //System.out.println("LOG: " + line);
        notifyObservers(line);
    }
}
