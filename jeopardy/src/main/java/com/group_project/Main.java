package com.group_project;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
       Scanner scanner = new Scanner(System.in);
       System.out.println("Welcome to Jeopardy!");
       
       //enter number of players
       System.out.print("Enter number of players: ");
       int numPlayers = scanner.nextInt();

         //create players
         for (int i = 1; i <= numPlayers; i++) {
             System.out.print("Enter name for Player " + i + ": ");
             String playerName = scanner.next();
             Player player = new Player(playerName);
             Game game = new Game();
             game.addPlayer(player);
         }

         // load questions
        QuestionLoader questionLoader = new QuestionLoader(new QuestionBank("System"));
        questionLoader.loadQuestionsFromFile("sample_game_JSON.json");

        QuestionBank system = questionLoader.getSystem();
        if (system == null) {
            System.out.println("No questions loaded. Exiting.");
            return;
        }
        
        // Display question table
        QuestionTableDisplay tableDisplay = new QuestionTableDisplay();
        tableDisplay.displayTable();
        
        
        while (true) {
            System.out.print("Type 'exit' to quit or press Enter to continue: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the game. Goodbye!");
                break;
            }
            // Example of asking a question (to be expanded)
            System.out.print("Enter category to select a question from: ");
            String category= scanner.nextLine();
            
            QuestionBank selectedCategory = system.getCategoryByName(category);
            System.out.println("Selected category: " + category);
            if (selectedCategory == null) {
                System.out.println("Category not found!");
                continue;
            }


            System.out.print("Enter value to select a question: ");
            int value = scanner.nextInt();

            QuestionGrabber questionToAsk = new QuestionGrabber(selectedCategory);
            Question question = questionToAsk.getQuestionByValue(value);
            if (question != null) {
                question.displayQuestion();
            } else {
                System.out.println("Question not found!");
            }

            System.out.println("pick answer: ");
            String answer = scanner.next();
            // Here you would check the answer and update scores accordingly
            if(question.getAnswer().equalsIgnoreCase(answer)) {
                System.out.println("Correct!");
                // Update player score logic here
            } else {
                System.out.println("Incorrect! The correct answer was: " + question.getAnswer());
            }
        } 
    }
}