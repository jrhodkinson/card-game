package jrh.game.event.impl;

import jrh.game.event.Event;

public class TurnEnded implements Event {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
