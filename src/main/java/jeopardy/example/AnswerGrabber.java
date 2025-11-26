package jeopardy.example;

public class AnswerGrabber {
    private Question currentQuestion;

    public AnswerGrabber(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public boolean checkAnswer(String playerAnswer) {
        String correctAnswer = currentQuestion.getAnswer();
        return correctAnswer.equalsIgnoreCase(playerAnswer);
    }
}
