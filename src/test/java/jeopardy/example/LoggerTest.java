package jeopardy.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoggerTest {

    @Test
    public void testLoggerImplementsEventPublisher() {
        Logger logger = Logger.getInstance();
        assertNotNull(logger, "Logger instance should not be null");
        assertTrue(logger instanceof EventPublisher, "Logger should implement EventPublisher");
    }

    @Test
    public void testActivityLoggerIntegration() {
        Logger logger = Logger.getInstance();
        ActivityLogger activityLogger = logger.getActivityLogger();
        assertNotNull(activityLogger, "ActivityLogger should not be null");
    }

    @Test
    public void testEventRecordBuilder() {
        EventRecord event = new EventRecord.Builder("player1", "Start Game")
            .caseId("case123")
            .category("Science")
            .questionValue("200")
            .build();
        
        assertEquals("player1", event.getPlayerId());
        assertEquals("Start Game", event.getActivity());
        assertEquals("case123", event.getCaseId());
        assertEquals("Science", event.getCategory());
        assertEquals("200", event.getQuestionValue());
        assertNotNull(event.getTimestamp());
    }

    @Test
    public void testEventRecordToLogLine() {
        EventRecord event = new EventRecord.Builder("player1", "Answer Question")
            .caseId("case123")
            .category("Variables & Data Types")
            .questionValue("100")
            .answerGiven("A")
            .result("Correct")
            .scoreAfterPlay("100")
            .build();
        
        String logLine = event.toLogLine();
        assertTrue(logLine.contains("player1"), "Log line should contain player ID");
        assertTrue(logLine.contains("Answer Question"), "Log line should contain activity");
        assertTrue(logLine.contains("case123"), "Log line should contain case ID");
    }
}
