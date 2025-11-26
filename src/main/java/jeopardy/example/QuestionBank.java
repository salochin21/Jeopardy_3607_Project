package jeopardy.example;

import java.util.ArrayList;

public class QuestionBank implements QuestionComponent {
    // This class will manage the collection of questions for the game

    private ArrayList<QuestionComponent> category;
    private String name;

    public QuestionBank(String Category) {
        this.name = Category;
        this.category = new ArrayList<>();
        //System.out.println("Created QuestionBank for category: " + Category);
    }

    //accessor
    public String getName() {
        return name;
    }


    public void addQuestion(QuestionComponent question) {
        category.add(question);
    }

    public ArrayList<QuestionComponent> getQuestions() {
        return category;
    }

    public boolean hasCategory(String categoryName) {
        for (QuestionComponent question : category) {
            if (question instanceof QuestionBank) {
                QuestionBank q = (QuestionBank) question;
                if (q.getName().equalsIgnoreCase(categoryName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public QuestionBank getCategoryByName(String categoryName) {
        for (QuestionComponent question : category) {
            if (question instanceof QuestionBank) {
                QuestionBank q = (QuestionBank) question;
                if (q.getName().equalsIgnoreCase(categoryName)) {
                    //System.out.println("Found category: " + categoryName);
                    return q;
                }
            }
        }
        return null;
    }

    

    @Override
    public void displayQuestion() {
        System.out.println("\nCategory: " + name);
        for (QuestionComponent question : category) {
            question.displayQuestion();
        }
    }
}
