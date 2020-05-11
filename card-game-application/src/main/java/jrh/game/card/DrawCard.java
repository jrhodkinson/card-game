package jrh.game.card;

import jrh.game.match.Match;
import jrh.game.match.Target;

public class DrawCard extends Card {

    private final int amountToDraw;

    public DrawCard(int amountToDraw) {
        super(2);
        this.amountToDraw = amountToDraw;
    }

    @Override
    public void play(Match match, Target target) {
        match.getActivePlayer().drawToHand(amountToDraw);
    }

    @Override
    public String toString() {
        return String.format("draw %d (%d)", amountToDraw, getCost());
    }
}
