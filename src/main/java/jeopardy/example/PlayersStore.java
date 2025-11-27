package jeopardy.example;

import java.util.ArrayList;

public class PlayersStore {
    private ArrayList<Player> players;
    
    

    public PlayersStore() {
        players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
        Logger.getInstance().logEvent(player.getName(), "PLAYER_ADDED", ",", ",", ",", ",", ",");
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayerByName(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public Player getPlayerbyindex(int index) {
        return players.get(index);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public void addScore(String playerName, int score) {
        Player player = getPlayerByName(playerName);
        if (player != null) {
            player.addToScore(score);
        }
    }
}
