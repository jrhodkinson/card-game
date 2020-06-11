package jrh.game.api.event;

import jrh.game.common.User;
import jrh.game.common.event.Event;

public class MatchEnded implements Event {

    private final User winner;

    public MatchEnded(User winner) {
        this.winner = winner;
    }

    public User getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        return String.format("%s winner=%s", getClass().getSimpleName(), winner);
    }
}
