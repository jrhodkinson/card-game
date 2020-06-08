package jrh.game.card.behaviour;

import jrh.game.asset.JsonKey;
import jrh.game.card.event.CardResolved;
import jrh.game.event.Callback;
import jrh.game.event.EventHandler;
import jrh.game.event.Subscribe;
import jrh.game.match.CardFlowController;
import jrh.game.match.api.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("vanish")
public class VanishBehaviour extends Behaviour implements EventHandler {

    private static final Logger logger = LogManager.getLogger(VanishBehaviour.class);

    public VanishBehaviour() {
        super(false);
    }

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match, Callback callback) {
        if (cardResolved.getCard().equals(this.getCard())) {
            logger.info("Vanishing played card={}", cardResolved.getCard());
            match.getController(CardFlowController.class).destroyPlayedCard(this.getCard());
            callback.unregister();
        }
    }

    @Override
    public VanishBehaviour duplicate() {
        return new VanishBehaviour();
    }
}
