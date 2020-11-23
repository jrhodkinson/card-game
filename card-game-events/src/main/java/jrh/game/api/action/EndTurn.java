package jrh.game.api.action;

import jrh.game.api.Action;
import jrh.game.common.User;

public class EndTurn implements Action {

    private final User user;

    public EndTurn(User user) {
        this.user = user;
    }

    @Override
    public User getActor() {
        return user;
    }
}
