package com.group_project;

public class Question implements QuestionComponent {
    
    private String category;
    private String questionText;
    private String answer;
    private int value;
    private boolean isAsked;
    private String[] options; // For multiple-choice questions

    public Question(String category, String questionText, String answer, int value, String[] options) {
        this.questionText = questionText;
        this.answer = answer;
        this.value = value;
        this.options = options;
        this.isAsked = false;
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

    @Override
    public void displayQuestion() {
        System.out.println(category + "\n"+ questionText);
        for (int i = 0; i < options.length; i++) {
            System.out.println((char)('A' + i) + ": " + options[i]);
        }
    }
    
}
