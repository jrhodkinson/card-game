package jrh.game.card.behaviour;

import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardResolved;
import jrh.game.asset.JsonKey;
import jrh.game.common.EventHandler;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Keyword;
import jrh.game.match.CardFlowController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.NO_TARGET;

@JsonKey("vanish")
public class VanishBehaviour extends AbstractBehaviour implements EventHandler {

    private static final Logger logger = LogManager.getLogger(VanishBehaviour.class);

    public VanishBehaviour() {
        super(NO_TARGET);
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().keyword(Keyword.VANISH).build();
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
