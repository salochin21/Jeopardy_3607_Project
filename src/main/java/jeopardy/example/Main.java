package jeopardy.example;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        game gameInstance = new game();
        gameInstance.introGame(scanner);
        // register file observer for logging and create a case id
        Logger logger = Logger.getInstance();
        logger.addObserver(new FileObserver("game.csv"));
        logger.setCaseId(UUID.randomUUID().toString());

        GamePlay gamePlay = new GamePlay(gameInstance.strategy.getSystem(),gameInstance.playersStore);
        gamePlay.playing(scanner);


    }

    

    
}