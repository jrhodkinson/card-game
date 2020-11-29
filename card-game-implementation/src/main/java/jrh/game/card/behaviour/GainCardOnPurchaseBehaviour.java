package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardPurchased;
import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.CardId;
import jrh.game.match.CardFlowController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("gainCardOnPurchase")
public class GainCardOnPurchaseBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(GainCardOnPurchaseBehaviour.class);

    @JsonValue
    private final CardId cardId;

    @JsonCreator
    public GainCardOnPurchaseBehaviour(CardId cardId) {
        super(false);
        this.cardId = cardId;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().plainString("On purchase,").keyword("gain").card(cardId)
                .build();
    }

    @Subscribe
    private void cardPurchased(CardPurchased cardPurchased, Match match) {
        if (cardPurchased.getCard().equals(this.getCard())) {
            logger.info("Gaining card with cardId={} for player={}", cardId, cardPurchased.getPurchaser());
            match.getController(CardFlowController.class).gainDirectly(cardId, cardPurchased.getPurchaser().getUser());
        }
    }

    @Override
    public GainCardOnPurchaseBehaviour duplicate() {
        return new GainCardOnPurchaseBehaviour(cardId);
    }
}
