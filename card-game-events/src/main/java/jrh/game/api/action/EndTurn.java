package jrh.game.api.action;

import jrh.game.common.User;

public class EndTurn implements Action {

    private final User user;

    public EndTurn(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
