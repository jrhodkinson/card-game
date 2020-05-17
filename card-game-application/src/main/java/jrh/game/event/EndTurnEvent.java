package jrh.game.event;

import jrh.game.match.Match;
import jrh.game.match.Player;

public class EndTurnEvent implements Event {

    private final Match match;
    private final Player player;

    public EndTurnEvent(Match match, Player player) {
        this.match = match;
        this.player = player;
    }

    public Match getMatch() {
        return match;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return String.format("%s for %s", getClass().getSimpleName(), player.getUser());
    }
}
