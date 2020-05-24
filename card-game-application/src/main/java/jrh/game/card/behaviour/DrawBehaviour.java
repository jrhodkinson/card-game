package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.CardPlayed;
import jrh.game.match.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DrawBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(DrawBehaviour.class);

    @JsonValue
    private final int amountToDraw;

    public DrawBehaviour(int amountToDraw) {
        this.amountToDraw = amountToDraw;
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard())) {
            logger.info("Drawing {} card(s) for active player={}", amountToDraw, match.getActivePlayer());
            match.getCardFlowController().drawCards(match.getActivePlayer(), amountToDraw);
        }
    }

    @Override
    public DrawBehaviour duplicate() {
        return new DrawBehaviour(amountToDraw);
    }
}
