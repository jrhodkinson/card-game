package jrh.game.card.behaviour;

import jrh.game.match.Match;

public class DrawBehaviour implements BasicBehaviour {

    private final int amountToDraw;

    public DrawBehaviour(int amountToDraw) {
        this.amountToDraw = amountToDraw;
    }

    @Override
    public void play(Match match) {
        match.getActivePlayer().drawToHand(amountToDraw);
    }
}
