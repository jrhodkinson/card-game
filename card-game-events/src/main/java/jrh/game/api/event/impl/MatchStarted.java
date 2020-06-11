package jrh.game.api.event.impl;

import jrh.game.api.event.Event;

public class MatchStarted implements Event {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
