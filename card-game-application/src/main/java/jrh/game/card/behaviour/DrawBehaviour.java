package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.asset.JsonKey;
import jrh.game.card.event.CardPlayed;
import jrh.game.common.BehaviourDescription;
import jrh.game.event.Subscribe;
import jrh.game.match.CardFlowController;
import jrh.game.api.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("draw")
public class DrawBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(DrawBehaviour.class);

    @JsonValue
    private final int amountToDraw;

    public DrawBehaviour(int amountToDraw) {
        super(false);
        this.amountToDraw = amountToDraw;
    }

    @Override
    public BehaviourDescription getDescription() {
        return BehaviourDescription.builder().keyword("Draw").number(amountToDraw).build();
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
