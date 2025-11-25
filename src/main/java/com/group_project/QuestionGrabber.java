package com.group_project;

public class QuestionGrabber {
    private QuestionBank questionBank;
    private Question question;

    public QuestionGrabber(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }
    
    public Question getQuestionByValue(int value) {
        for (QuestionComponent question : questionBank.getQuestions()) {
            if (question instanceof Question && !((Question) question).isAsked()) {
                Question q = (Question) question;
                if (q.getValue() == value) {
                    setQuestion(q);
                    return q;
                }
            }
        }
        return null;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    public Question getQuestion() {
        return question;
    }
}
