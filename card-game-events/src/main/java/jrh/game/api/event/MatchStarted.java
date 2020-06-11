package jrh.game.api.event;

import jrh.game.common.event.Event;

public class MatchStarted implements Event {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
