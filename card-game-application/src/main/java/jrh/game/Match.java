package jrh.game;

import jrh.game.action.Action;

import java.util.ArrayList;
import java.util.List;

public class Match {

    private final List<Player> players;
    private int currentPlayer = 0;

    public Match(List<Player> players) {
        this.players = new ArrayList<>(players);
    }

    public void accept(Action action) {
        action.applyTo(this);
    }

    public Player currentPlayer() {
        return players.get(currentPlayer);
    }

    public void advanceToNextPlayer() {
        currentPlayer++;
        if (currentPlayer >= players.size()) {
            currentPlayer = 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            builder.append(players.get(i));
            if (i == currentPlayer) {
                builder.append(" (*)");
            }
            builder.append("\n");
        }
        builder.deleteCharAt(builder.lastIndexOf("\n"));
        return builder.toString();
    }
}
