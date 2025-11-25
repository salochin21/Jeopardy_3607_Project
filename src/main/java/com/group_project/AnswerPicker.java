package com.group_project;

public class AnswerPicker implements Command {
    
    private AnswerGrabber answerGrabber;
    private Question selectedQuestion;
    private Player currentPlayer;

    public AnswerPicker(QuestionGrabber questionGrabber, Player currentPlayer) {
        this.selectedQuestion = questionGrabber.getQuestion();
        this.currentPlayer = currentPlayer;
        this.answerGrabber = new AnswerGrabber(selectedQuestion);
    }

    @Override
    public void execute(String input) {
        if (answerGrabber.checkAnswer(input)) {
            System.out.println("Correct answer!");
            currentPlayer.addToScore(selectedQuestion.getValue());
            selectedQuestion.setAsked(true);
            // Log structured answer event AFTER updating score
            Logger.getInstance().logEvent(
                    currentPlayer.getName(),
                    "ANSWER",
                    selectedQuestion.getCategory(),
                    String.valueOf(selectedQuestion.getValue()),
                    input,
                    "CORRECT",
                    String.valueOf(currentPlayer.getScore())
            );
        } else {
            System.out.println("Wrong answer!");
            Logger.getInstance().logEvent(
                    currentPlayer.getName(),
                    "ANSWER",
                    selectedQuestion.getCategory(),
                    String.valueOf(selectedQuestion.getValue()),
                    input,
                    "INCORRECT",
                    String.valueOf(currentPlayer.getScore())
            );
        }
    }
    
}
