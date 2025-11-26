package jeopardy.example;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class QuestionPickerTest {
    
    private static QuestionBank category;
    private static Player player;
    private static QuestionBank system;

    @BeforeAll
    public static void runOnceBeforeTests()throws IOException{
        JsonLoader loader = new JsonLoader("sample_game_JSON.json");
        assertNotNull(loader.readFromFile("sample_game_JSON.json"));
        player = new Player("TestPlayer");
        
        loader.loadQuestions();
        system = loader.getSystem();
        category = system.getCategoryByName("Variables & Data Types");

    }

    @Test
    public void testQuestionPickerFunctionality()throws IOException{
        //pick a question
        QuestionGrabber questionGrabber = new QuestionGrabber(category);
        player.getRemoteControl().setCommand(new QuestionPicker(questionGrabber, player));
        player.getRemoteControl().sendCommand("100");
        assertTrue("Which of the following declares an integer variable in C++?".equals(questionGrabber.getQuestionByValue(100).getQuestionText()), "Question should be picked successfully");
    }

    @Test
    public void selectQuestionThatDoesNotExist()throws IOException{
        //pick a question
        QuestionGrabber questionGrabber = new QuestionGrabber(category);
        player.getRemoteControl().setCommand(new QuestionPicker(questionGrabber, player));
        player.getRemoteControl().sendCommand("999");
        assertTrue(questionGrabber.getQuestionByValue(999)==null, "Question should not exist");
    }


}
