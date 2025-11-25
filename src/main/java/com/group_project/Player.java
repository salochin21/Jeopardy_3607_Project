package com.group_project;

public class Player {
    
    private String name;
    private int score;

    private RemoteControl remoteControl;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.remoteControl = new RemoteControl();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addToScore(int amount) {
        this.score += amount;
    }

    public RemoteControl getRemoteControl() {
        return remoteControl;
    }
}
