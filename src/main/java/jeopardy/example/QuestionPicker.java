package jeopardy.example;

public class QuestionPicker implements Command{
     private QuestionGrabber questionGrabber;
    private QuestionBank questionBank;
    private Question selectedQuestion;
    private Player currentPlayer;

    public QuestionPicker(QuestionGrabber questionGrabber, Player currentPlayer) {
        this.questionGrabber = questionGrabber;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void execute(String input) {
        selectedQuestion = questionGrabber.getQuestionByValue(Integer.valueOf(input));
        if (selectedQuestion != null && selectedQuestion.isAsked()==false) {
            // Log question picked using structured format
            Logger.getInstance().logEvent(
                currentPlayer != null ? currentPlayer.getName() : "",
                "QUESTION_PICKED",
                selectedQuestion.getCategory(),
                String.valueOf(selectedQuestion.getValue()),
                "",
                "",
                String.valueOf(currentPlayer != null ? currentPlayer.getScore() : 0)
            );
            selectedQuestion.displayQuestion();
            } else {
                System.out.println("Question already asked or does not exist. Please pick another question.");
            }
    }
    
}
