package com.group_project;

public class QuestionGrabber {
    private QuestionBank questionBank;

    public QuestionGrabber(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }
    
    public Question getQuestionByValue(int value) {
        for (QuestionComponent question : questionBank.getQuestions()) {
            if (question instanceof Question) {
                Question q = (Question) question;
                if (q.getValue() == value) {
                    return q;
                }
            }
        }
        return null;
    }
}
