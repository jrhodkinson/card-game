package jrh.game.api.event.impl;

import jrh.game.api.event.Event;
import jrh.game.api.Player;

public class TurnEnded implements Event {

    private final Player previousPlayer;
    private final Player newPlayer;

    public TurnEnded(Player previousPlayer, Player newPlayer) {
        this.previousPlayer = previousPlayer;
        this.newPlayer = newPlayer;
    }

    public Player getPreviousPlayer() {
        return previousPlayer;
    }

    public Player getNewPlayer() {
        return newPlayer;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
