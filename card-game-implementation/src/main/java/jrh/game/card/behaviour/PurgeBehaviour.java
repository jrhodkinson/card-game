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

import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.STORE;

@JsonKey("purge")
public class PurgeBehaviour extends AbstractBehaviour implements EventHandler {

    private static final Logger logger = LogManager.getLogger(PurgeBehaviour.class);

    public PurgeBehaviour() {
        super(STORE);
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().keyword(Keyword.PURGE).number(1).build();
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard()) && cardPlayed.getStoreTarget().isPresent()) {
            EntityId target = cardPlayed.getStoreTarget().get();
            logger.info("Purging target={} from the store", target);
            match.getController(CardFlowController.class).destroyStoreCard(target);
        }
    }

    @Override
    public PurgeBehaviour duplicate() {
        return new PurgeBehaviour();
    }
}