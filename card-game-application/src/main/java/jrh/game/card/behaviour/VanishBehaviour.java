package jrh.game.card.behaviour;

import jrh.game.asset.JsonKey;
import jrh.game.api.event.CardResolved;
import jrh.game.common.BehaviourDescription;
import jrh.game.api.Callback;
import jrh.game.common.EventHandler;
import jrh.game.api.Subscribe;
import jrh.game.match.CardFlowController;
import jrh.game.api.Match;
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
    private void cardResolved(CardResolved cardResolved, Match match, Callback callback) {
        if (cardResolved.getCard().equals(this.getCard())) {
            logger.info("Vanishing played card={}", cardResolved.getCard());
            match.getController(CardFlowController.class).destroyPlayedCard(this.getCard());
            callback.unregister(this);
        }
    }

    @Override
    public VanishBehaviour duplicate() {
        return new VanishBehaviour();
    }
}
