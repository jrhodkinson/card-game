package jrh.game.api.event.impl;

import jrh.game.api.event.Event;
import jrh.game.api.Player;

public class PlayerTookDamage implements Event {

    private final Player player;

    public PlayerTookDamage(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return String.format("%s player=%s", getClass().getSimpleName(), player);
    }
}
