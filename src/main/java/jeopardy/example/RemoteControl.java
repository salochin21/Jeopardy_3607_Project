package jeopardy.example;

public class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }                       
    public void sendCommand( String input) {
        command.execute(input);
    }
}
