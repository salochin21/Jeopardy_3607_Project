package jeopardy.example;

public class CSVLoader implements Strategy {
    private QuestionBank questionBank;

    public CSVLoader() {
       
    }

    @Override
    public void loadQuestions() {
        // Implementation for loading questions from CSV
        // and adding them to questionBank
    }

    @Override
    public QuestionBank getSystem() {
        return questionBank;
    }
    
}
