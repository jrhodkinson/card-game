package jrh.game.event.impl;

import jrh.game.event.Event;
import jrh.game.match.Match;

public class StartedMatch implements Event {

    private final Match match;

    public StartedMatch(Match match) {
        this.match = match;
    }

    public Match getMatch() {
        return match;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
