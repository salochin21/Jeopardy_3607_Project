package jeopardy.example;

import java.time.Instant;

/**
 * Immutable event record for process mining.
 * Adheres to Single Responsibility and Immutability principles.
 */
public class EventRecord {
    private final String caseId;
    private final String playerId;
    private final String activity;
    private final Instant timestamp;
    private final String category;
    private final String questionValue;
    private final String answerGiven;
    private final String result;
    private final String scoreAfterPlay;

    private EventRecord(Builder builder) {
        this.caseId = builder.caseId;
        this.playerId = builder.playerId;
        this.activity = builder.activity;
        this.timestamp = builder.timestamp != null ? builder.timestamp : Instant.now();
        this.category = builder.category;
        this.questionValue = builder.questionValue;
        this.answerGiven = builder.answerGiven;
        this.result = builder.result;
        this.scoreAfterPlay = builder.scoreAfterPlay;
    }

    // Getters
    public String getCaseId() {
        return caseId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getActivity() {
        return activity;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestionValue() {
        return questionValue;
    }

    public String getAnswerGiven() {
        return answerGiven;
    }

    public String getResult() {
        return result;
    }

    public String getScoreAfterPlay() {
        return scoreAfterPlay;
    }

    /**
     * Converts event to pipe-separated format for observer notification.
     */
    public String toLogLine() {
        return String.join("  ", 
            caseId != null ? caseId : "",
            playerId != null ? playerId : "",
            activity != null ? activity : "",
            timestamp.toString(),
            category != null ? category : "",
            questionValue != null ? questionValue : "",
            answerGiven != null ? answerGiven : "",
            result != null ? result : "",
            scoreAfterPlay != null ? scoreAfterPlay : ""
        );
    }

    /**
     * Builder pattern for flexible EventRecord creation.
     */
    public static class Builder {
        private final String playerId;
        private final String activity;
        private String caseId;
        private Instant timestamp;
        private String category = "";
        private String questionValue = "";
        private String answerGiven = "";
        private String result = "";
        private String scoreAfterPlay = "";

        public Builder(String playerId, String activity) {
            this.playerId = playerId;
            this.activity = activity;
        }

        public Builder caseId(String caseId) {
            this.caseId = caseId;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder category(String category) {
            this.category = category != null ? category : "";
            return this;
        }

        public Builder questionValue(String questionValue) {
            this.questionValue = questionValue != null ? questionValue : "";
            return this;
        }

        public Builder answerGiven(String answerGiven) {
            this.answerGiven = answerGiven != null ? answerGiven : "";
            return this;
        }

        public Builder result(String result) {
            this.result = result != null ? result : "";
            return this;
        }

        public Builder scoreAfterPlay(String scoreAfterPlay) {
            this.scoreAfterPlay = scoreAfterPlay != null ? scoreAfterPlay : "";
            return this;
        }

        public EventRecord build() {
            return new EventRecord(this);
        }
    }
}
