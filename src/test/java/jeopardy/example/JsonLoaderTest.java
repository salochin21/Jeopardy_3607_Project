package jeopardy.example;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class JsonLoaderTest {

    @Test
    public void loadQuestions_populatesQuestionBank() throws IOException {
        JsonLoader loader = new JsonLoader("sample_game_JSON.json");
        assertNotNull(loader.readFromFile("sample_game_JSON.json"));

        loader.loadQuestions();
        QuestionBank system = loader.getSystem();
        assertNotNull(system, "System QuestionBank should not be null");

        // There should be at least one category created from the sample JSON
        assertTrue(system.getQuestions().size() > 0, "System should contain categories after load");

        // Verify a known category exists and has questions
        assertTrue(system.hasCategory("Variables & Data Types"), "Expected category missing");
        QuestionBank category = system.getCategoryByName("Variables & Data Types");
        assertNotNull(category, "Category 'Variables & Data Types' should be present");
        assertTrue(category.getQuestions().size() > 0, "Category should contain questions");
    }

    @Test
    public void allQuestions_areParsedIntoCategories() throws IOException {
        JsonLoader loader = new JsonLoader("sample_game_JSON.json");
        loader.loadQuestions();
        QuestionBank system = loader.getSystem();

        int totalQuestions = 0;
        for (QuestionComponent comp : system.getQuestions()) {
            if (comp instanceof QuestionBank) {
                totalQuestions += ((QuestionBank) comp).getQuestions().size();
            }
        }

        assertTrue(totalQuestions >= 1, "Total parsed questions should be at least 1");
    }
}
