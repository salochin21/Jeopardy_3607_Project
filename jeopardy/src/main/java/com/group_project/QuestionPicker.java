package com.group_project;

public class QuestionPicker implements Command{
    private QuestionGrabber questionGrabber;

    public QuestionPicker(QuestionGrabber questionGrabber) {
        this.questionGrabber = questionGrabber;
    }

    @Override
    public void execute(String input) {

        questionGrabber.getQuestionByValue(Integer.valueOf(input));
    }
}
