package jrh.game.api.action;

import jrh.game.api.ControllableMatch;
import jrh.game.common.User;
import jrh.game.match.MatchStateController;

public class EndTurn implements Action {

    private final User user;

    public EndTurn(User user) {
        this.user = user;
    }

    @Override
    public void perform(ControllableMatch match) {
        if (match.getActivePlayer().getUser().equals(user)) {
            match.getController(MatchStateController.class).endTurn();
        }
    }
}
