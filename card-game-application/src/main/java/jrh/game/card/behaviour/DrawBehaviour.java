package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.match.Match;

public class DrawBehaviour implements BasicBehaviour {

    @JsonValue
    private final int amountToDraw;

    public DrawBehaviour(int amountToDraw) {
        this.amountToDraw = amountToDraw;
    }

    @Override
    public void play(Match match) {
        match.getCardFlowController().drawCards(match.getActivePlayer(), amountToDraw);
    }
}
