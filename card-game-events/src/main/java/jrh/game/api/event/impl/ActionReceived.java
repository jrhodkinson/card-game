package jrh.game.api.event.impl;

import jrh.game.api.action.Action;
import jrh.game.api.event.Event;

public class ActionReceived implements Event {

    private final Action action;

    public ActionReceived(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getClass().getSimpleName(), action);
    }
}
