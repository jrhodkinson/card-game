package jrh.game.action;

import jrh.game.Match;

public class EndTurn implements Action {

    @Override
    public void applyTo(Match match) {
        match.advanceToNextPlayer();
    }
}
