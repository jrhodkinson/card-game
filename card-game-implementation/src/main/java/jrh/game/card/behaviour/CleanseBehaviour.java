package jrh.game.card.behaviour;

import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardPlayed;
import jrh.game.asset.JsonKey;
import jrh.game.common.EntityId;
import jrh.game.common.EventHandler;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Keyword;
import jrh.game.match.CardFlowController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.HAND;

@JsonKey("cleanse")
public class CleanseBehaviour extends AbstractBehaviour implements EventHandler {

    private static final Logger logger = LogManager.getLogger(CleanseBehaviour.class);

    public CleanseBehaviour() {
        super(HAND);
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().keyword(Keyword.CLEANSE).number(1).build();
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard()) && cardPlayed.getCardInHandTarget().isPresent()) {
            EntityId target = cardPlayed.getCardInHandTarget().get();
            if (target.equals(cardPlayed.getCard().getEntityId())) {
                logger.warn("{} cannot destroy itself", getClass().getSimpleName());
            } else {
                logger.info("Destroying card in hand={}", target);
                match.getController(CardFlowController.class).destroyCardInHand(cardPlayed.getPlayer(), target);
            }
        }
    }

    @Override
    public CleanseBehaviour duplicate() {
        return new CleanseBehaviour();
    }

}
