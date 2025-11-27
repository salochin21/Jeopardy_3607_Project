package jeopardy.example;

import java.util.Scanner;

//game`initial class
public class game {
    Logger logger;
    PlayersStore playersStore;
    Strategy strategy;
    public game() {
        logger = Logger.getInstance();
    }
    public void introGame(Scanner scanner) {
        System.out.println("Welcome to Jeopardy!");
        System.out.println("Start Game");
        scanner.nextLine();

        startGame(scanner);
        
    }

    private void startGame(Scanner scanner) {
        chooseStrategy(scanner);
        System.out.print("Enter number of players: ");
        int input=scanner.nextInt();
        createPlayers(scanner, input);
        System.out.println("Starting the game...");
        Logger.getInstance().logEvent("", "GAME_STARTED", "", "", "", "", String.valueOf(playersStore.getNumberOfPlayers()));
        
    }

    private void createPlayers(Scanner scanner, int numPlayers) {
        playersStore = new PlayersStore();
        scanner.nextLine(); // Consume newline
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String playerName = scanner.nextLine();
            Player player = new Player(playerName);
            // Add player to the game
            playersStore.addPlayer(player);
            Logger.getInstance().logEvent(
                playerName+",",
                "PLAYER_ADDED",
                "1",
                "1",
                "1",
                playerName+"",
                "1"
            );}
    }

    private void chooseStrategy(Scanner scanner) {
        System.out.println("Choose question loading strategy:");
        System.out.println("1. JSON Loader");
        System.out.println("2. CSV Loader");
        System.out.print("Please select an option: ");
        String input = scanner.nextLine();
        
        switch (input) {
            case "1":
                System.out.println("enter file path:");
                
                String filepath= scanner.nextLine();
                logger.logEvent("", "STRATEGY_SELECTED:Json", ",", ",", ",", ",", ",");
                strategy = new JsonLoader(filepath);
                System.out.println("Questions will be loaded from JSON.");
                logger.logEvent("", "Questions Loaded from Json", "", "", "", "", filepath);
                break;
            case "2":
                
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                chooseStrategy(scanner);
                return;
        }
        
        strategy.loadQuestions();
    }

    public QuestionBank getQuestionBank() {
        return strategy.getSystem();
    }

    public PlayersStore getPlayersStore() {
        return playersStore;
    }
}
