package jrh.game.card.behaviour;

import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardResolved;
import jrh.game.asset.JsonKey;
import jrh.game.common.BehaviourDescription;
import jrh.game.common.EventHandler;
import jrh.game.match.CardFlowController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("vanish")
public class VanishBehaviour extends AbstractBehaviour implements EventHandler {

    private static final Logger logger = LogManager.getLogger(VanishBehaviour.class);

    public VanishBehaviour() {
        super(false);
    }

    @Override
    public BehaviourDescription getDescription() {
        return BehaviourDescription.keyword("Vanish");
    }

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match) {
        if (cardResolved.getCard().equals(this.getCard())) {
            logger.info("Vanishing played card={}", cardResolved.getCard());
            match.getController(CardFlowController.class).destroyPlayedCard(this.getCard());
        }
    }

    @Override
    public VanishBehaviour duplicate() {
        return new VanishBehaviour();
    }
}
