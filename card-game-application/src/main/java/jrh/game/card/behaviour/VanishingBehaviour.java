package jrh.game.card.behaviour;

import jrh.game.card.event.CardResolved;
import jrh.game.event.Callback;
import jrh.game.event.EventHandler;
import jrh.game.event.Subscribe;
import jrh.game.match.CardFlowController;
import jrh.game.match.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanishingBehaviour extends Behaviour implements EventHandler {

    private static final Logger logger = LogManager.getLogger(VanishingBehaviour.class);

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match, Callback callback) {
        if (cardResolved.getCard().equals(this.getCard())) {
            logger.info("Vanishing played card={}", cardResolved.getCard());
            match.getController(CardFlowController.class).destroyPlayedCard(this.getCard());
            callback.unregister();
        }
    }

    @Override
    public VanishingBehaviour duplicate() {
        return new VanishingBehaviour();
    }
}
