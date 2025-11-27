package jeopardy.example;

public interface Strategy {    
    public void loadQuestions();
    public QuestionBank getSystem();
}
