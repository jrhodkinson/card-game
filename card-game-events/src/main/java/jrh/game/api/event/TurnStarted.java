package jrh.game.api.event;

import jrh.game.api.Event;
import jrh.game.api.Player;

public class TurnStarted implements Event {

    private final Player newPlayer;

    public TurnStarted(Player newPlayer) {
        this.newPlayer = newPlayer;
    }

    public Player getNewPlayer() {
        return newPlayer;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
