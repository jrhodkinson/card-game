package jrh.game.action;

import jrh.game.match.MatchStateController;
import jrh.game.api.Match;

public class EndTurn implements Action {

    private final Match match;

    public EndTurn(Match match) {
        this.match = match;
    }

    @Override
    public void perform() {
        match.getController(MatchStateController.class).endTurn();
    }
}
