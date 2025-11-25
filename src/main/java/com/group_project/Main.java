package com.group_project;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
       Scanner scanner = new Scanner(System.in);
       
         System.out.println("Welcome to Jeopardy!");
        // register file observer for logging and create a case id
        Logger logger = Logger.getInstance();
        logger.addObserver(new FileObserver("game.log"));
        logger.setCaseId(UUID.randomUUID().toString());
       Game game = new Game();
       //enter number of players
    System.out.print("Enter number of players: ");
    int numPlayers = scanner.nextInt();

         //create players
         for (int i = 1; i <= numPlayers; i++) {
             System.out.print("Enter name for Player " + i + ": ");
             String playerName = scanner.next();
             Player player = new Player(playerName);
             
             game.addPlayer(player);
             // Log player creation (other fields empty as not applicable)
             Logger.getInstance().logEvent(playerName, "PLAYER_CREATED", "", "", "", "", String.valueOf(player.getScore()));
         }

         // load questions
        QuestionLoader questionLoader = new QuestionLoader(new QuestionBank("System"));
        questionLoader.loadQuestionsFromFile("sample_game_JSON.json");

        // Log file loaded and number of players selected
        Logger.getInstance().logEvent("", "FILE_LOADED", "", "", "sample_game_JSON.json", "", String.valueOf(game.getNumberOfPlayers()));

        QuestionBank system = questionLoader.getSystem();
        if (system == null) {
            System.out.println("No questions loaded. Exiting.");
            return;
        }
        
        // Display question table
        QuestionTableDisplay tableDisplay = new QuestionTableDisplay();
        tableDisplay.displayTable();
        scanner.nextLine(); 
        Player currentPlayer = null;
        String input;
        int index=0;
        Command command;
        System.out.println("Starting the game..."+game.getNumberOfPlayers()+" players joined.");
        Logger.getInstance().logEvent("", "GAME_STARTED", "", "", "", "", String.valueOf(game.getNumberOfPlayers()));
        while (true) {
            
            currentPlayer = game.getPlayerbyindex(index); 
            System.out.println("Player's turn:" + currentPlayer.getName());
            
            
            // Example of asking a question (to be expanded)
            
            System.out.print("Enter category to select a question from: ");
            input= scanner.nextLine();
            
            QuestionBank selectedCategory = system.getCategoryByName(input);
            System.out.println("Selected category: " + input);
            if (selectedCategory == null) {
                System.out.println("Category not found!");
                continue;
            }

            System.out.print("Enter value to select a question: ");
            input = scanner.next();
            
            QuestionGrabber questionGrabber = new QuestionGrabber(selectedCategory);

            currentPlayer.getRemoteControl().setCommand(new QuestionPicker(questionGrabber, currentPlayer));
            currentPlayer.getRemoteControl().sendCommand(input);
        
            
            

            if (questionGrabber.getQuestion() != null ){
                System.out.println("pick answer: ");
                input = scanner.next();
                // Here you would check the answer and update scores accordingly
                currentPlayer.getRemoteControl().setCommand(new AnswerPicker(questionGrabber, currentPlayer));
                currentPlayer.getRemoteControl().sendCommand(input);
                
            }
            
            scanner.nextLine(); // Consume newline
            
            System.out.println("Type 'exit' to quit or press Enter to continue: ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the game. Goodbye!");
                break;
            }
            index = (index + 1) % game.getNumberOfPlayers();
        } 
    }
}