package jeopardy.example;

public class Question implements QuestionComponent {
    private String category;
    private String questionText;
    private String answer;
    private int value;
    private boolean isAsked;
    private String[] options; // For multiple-choice questions

    public Question(String category, String questionText, String answer, int value, String[] options) {
        this.category = category;
        this.questionText = questionText;
        this.answer = answer;
        this.value = value;
        this.options = options;
        this.isAsked = false;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public int getValue() {
        return value;
    }

    public boolean isAsked() {
        return isAsked;
    }

    public void setAsked(boolean asked) {
        isAsked = asked;
    }

    @Override
    public void displayQuestion() {
        System.out.println("\n"+ questionText);
        for (int i = 0; i < options.length; i++) {
            System.out.println((char)('A' + i) + ": " + options[i]);
        }
    }
}
