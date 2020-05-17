package jrh.game.action;

import jrh.game.match.Match;

public class EndTurn implements Action {

    private final Match match;

    public EndTurn(Match match) {
        this.match = match;
    }

    @Override
    public void perform() {
        match.getMatchFlowManager().endTurn();
    }
}
