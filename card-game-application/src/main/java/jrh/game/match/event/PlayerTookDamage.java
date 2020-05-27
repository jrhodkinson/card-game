package jrh.game.match.event;

import jrh.game.event.Event;
import jrh.game.match.api.Player;

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
