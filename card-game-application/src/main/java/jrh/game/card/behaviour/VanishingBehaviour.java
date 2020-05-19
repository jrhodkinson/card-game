package jrh.game.card.behaviour;

import jrh.game.event.EventHandler;
import jrh.game.event.bus.Callback;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.CardResolved;
import jrh.game.match.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanishingBehaviour extends Behaviour implements EventHandler {

    private static final Logger logger = LogManager.getLogger(VanishingBehaviour.class);

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match, Callback callback) {
        if (cardResolved.getCard().equals(this.getCard())) {
            logger.info("Vanishing played card={}", cardResolved.getCard());
            match.getCardFlowController().destroyPlayedCard(this.getCard());
            callback.unregister();
        }
    }
}
