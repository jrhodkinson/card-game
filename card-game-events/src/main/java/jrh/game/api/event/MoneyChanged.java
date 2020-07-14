package jrh.game.api.event;

import jrh.game.api.Event;

public class MoneyChanged implements Event {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
