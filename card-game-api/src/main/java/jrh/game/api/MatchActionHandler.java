package jrh.game.api;

import jrh.game.api.action.Action;

public interface MatchActionHandler {
    void accept(Action action);
}
