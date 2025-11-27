package jeopardy.example;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AnswerPickerTest {
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
    public void testAnswerPickerFunctionality()throws IOException{
        //pick a question
        QuestionGrabber questionGrabber = new QuestionGrabber(category);
        player.getRemoteControl().setCommand(new QuestionPicker(questionGrabber, player));
        player.getRemoteControl().sendCommand("100");
        
        //pick an answer
        player.getRemoteControl().setCommand(new AnswerPicker(questionGrabber, player));
        player.getRemoteControl().sendCommand("A");
        
        //verify score update
        assertTrue(player.getScore() == 100, "Player score should be updated correctly for correct answer");
    }

    @Test
    public void testAnswerPickerWithWrongAnswer()throws IOException{
        //pick a question
        QuestionGrabber questionGrabber = new QuestionGrabber(category);
        player.getRemoteControl().setCommand(new QuestionPicker(questionGrabber, player));
        player.getRemoteControl().sendCommand("500");
        
        //pick a wrong answer
        player.getRemoteControl().setCommand(new AnswerPicker(questionGrabber, player));
        player.getRemoteControl().sendCommand("A");
        
        //verify score update
        assertTrue(player.getScore() == 0, "Player score should not change for wrong answer");
    }

    @Test
    public void testAnswerPickerWithNoQuestionPicked()throws IOException{
        //no question picked
        QuestionGrabber questionGrabber = new QuestionGrabber(category);
        
        //try to pick an answer
        player.getRemoteControl().setCommand(new AnswerPicker(questionGrabber, player));
        player.getRemoteControl().sendCommand("A");
        
        //verify score remains unchanged
        assertTrue(player.getScore() == 0, "Player score should remain unchanged when no question is picked");
    }

    @Test
    public void questionpickedOnlyOnce()throws IOException{
        //pick a question
        QuestionGrabber questionGrabber = new QuestionGrabber(category);
        player.getRemoteControl().setCommand(new QuestionPicker(questionGrabber, player));
        player.getRemoteControl().sendCommand("200");
        
        //pick a correct answer
        player.getRemoteControl().setCommand(new AnswerPicker(questionGrabber, player));
        player.getRemoteControl().sendCommand("B");
        Player tempPlayer = new Player("temp");
        //try to answer again
        tempPlayer.getRemoteControl().setCommand(new QuestionPicker(questionGrabber, player));
        tempPlayer.getRemoteControl().sendCommand("200");
        
        //verify score updated only once
        Question q =questionGrabber.getQuestionByValue(200);
        assertTrue(q.isAsked()==false, "");
    }
}
