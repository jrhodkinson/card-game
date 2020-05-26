package jrh.game.match.event;

import jrh.game.event.Event;

public class MatchStarted implements Event {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
