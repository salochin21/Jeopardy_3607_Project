package jeopardy.example;

/**
 * Interface for event publishing.
 * Adheres to Single Responsibility - responsible only for publishing events.
 */
public interface EventPublisher {
    void publish(EventRecord event);
    void addObserver(Observer observer);
}
