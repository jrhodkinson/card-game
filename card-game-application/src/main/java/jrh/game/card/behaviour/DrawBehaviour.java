package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.CardPlayed;
import jrh.game.match.Match;

public class DrawBehaviour extends Behaviour {

    @JsonValue
    private final int amountToDraw;

    public DrawBehaviour(int amountToDraw) {
        this.amountToDraw = amountToDraw;
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard())) {
            match.getCardFlowController().drawCards(match.getActivePlayer(), amountToDraw);
        }
    }
}
