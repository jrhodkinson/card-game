package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.asset.JsonKey;
import jrh.game.card.event.CardPlayed;
import jrh.game.event.Subscribe;
import jrh.game.match.CardFlowController;
import jrh.game.match.api.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("draw")
public class DrawBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(DrawBehaviour.class);

    @JsonValue
    private final int amountToDraw;

    public DrawBehaviour(int amountToDraw) {
        super(false);
        this.amountToDraw = amountToDraw;
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard())) {
            logger.info("Drawing {} card(s) for active player={}", amountToDraw, match.getActivePlayer());
            match.getController(CardFlowController.class).drawCards(match.getActivePlayer(), amountToDraw);
        }
    }

    @Override
    public DrawBehaviour duplicate() {
        return new DrawBehaviour(amountToDraw);
    }
}
