package jrh.game.action;

import jrh.game.match.MutableMatch;
import jrh.game.match.MatchStateController;

public class EndTurn implements Action {

    private final MutableMatch match;

    public EndTurn(MutableMatch match) {
        this.match = match;
    }

    @Override
    public void perform() {
        match.getController(MatchStateController.class).endTurn();
    }
}
