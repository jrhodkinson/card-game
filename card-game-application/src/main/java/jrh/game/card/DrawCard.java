package jrh.game.card;

import jrh.game.match.Match;
import jrh.game.match.Target;
import jrh.game.util.Colors;

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
        return String.format("%sdraw %d (%d)%s", Colors.BLUE, amountToDraw, getCost(), Colors.RESET);
    }
}
