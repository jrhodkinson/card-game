package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardPlayed;
import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.CardId;
import jrh.game.common.description.Keyword;
import jrh.game.match.CardFlowController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("gainCard")
public class GainCardBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(GainCardOnPurchaseBehaviour.class);

    @JsonValue
    private final CardId cardId;

    @JsonCreator
    public GainCardBehaviour(CardId cardId) {
        super(false);
        this.cardId = cardId;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().keyword(Keyword.GAIN).card(cardId).build();
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard())) {
            logger.info("Gaining card with cardId={} for player={}", cardId, cardPlayed.getPlayer());
            match.getController(CardFlowController.class).gainDirectly(cardId, cardPlayed.getPlayer().getUser());
        }
    }

    @Override
    public GainCardBehaviour duplicate() {
        return new GainCardBehaviour(cardId);
    }
}
