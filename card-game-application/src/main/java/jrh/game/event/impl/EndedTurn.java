package jrh.game.event.impl;

import jrh.game.event.Event;

public class EndedTurn implements Event {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
