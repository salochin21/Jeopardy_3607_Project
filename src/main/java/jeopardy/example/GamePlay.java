package jeopardy.example;

import java.util.Scanner;

public class GamePlay {

    QuestionBank questionBank;
    Player player;
    PlayersStore playersStore;
    QuestionTableDisplay tableDisplay;
    public GamePlay(QuestionBank questionBank,PlayersStore playersStore) {
        this.questionBank = questionBank;
        this.playersStore = playersStore;
        tableDisplay = new QuestionTableDisplay();
    }

    public void playing(Scanner scanner) {
        int index = 0;
        String input;
        while (true){
            tableDisplay.displayTable();
            player = playersStore.getPlayerbyindex(index);
            System.out.println("It's " + player.getName() + "'s turn.");
            // Here we would have logic for the player to pick a question

            System.out.print("Enter category to select a question from: ");
            input= scanner.nextLine();
            
            QuestionBank selectedCategory = questionBank.getCategoryByName(input);
            System.out.println("Selected category: " + input);
            if (selectedCategory == null) {
                System.out.println("Category not found!");
                continue;
            }

            System.out.print("Enter value to select a question: ");
            input = scanner.next();
            
            QuestionGrabber questionGrabber = new QuestionGrabber(selectedCategory);

            player.getRemoteControl().setCommand(new QuestionPicker(questionGrabber, player));
            player.getRemoteControl().sendCommand(input);
        
            
            

            if (questionGrabber.getQuestion() != null ){
                System.out.println("pick answer: ");
                input = scanner.next();
                // Here you would check the answer and update scores accordingly
                player.getRemoteControl().setCommand(new AnswerPicker(questionGrabber, player));
                player.getRemoteControl().sendCommand(input);
                
            }
            

            scanner.nextLine(); 
            
            System.out.println("Type 'exit' to quit or press Enter to continue: ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the game. Goodbye!");
                Summary summary = Logger.getInstance().getSummary();
                summary.displaySummary();
                break;
            }
            System.out.println('\f');
            System.out.flush();
            index = (index + 1) % playersStore.getNumberOfPlayers();
        }

        Summary summary = Logger.getInstance().getSummary();
        summary.displaySummary();
    }
    
    
}
