package jrh.game.card.behaviour;

import jrh.game.api.Callback;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardResolved;
import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;
import jrh.game.match.MatchStateController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.NO_TARGET;

@JsonKey("endTurn")
public class EndTurnBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(EndTurnBehaviour.class);

    public EndTurnBehaviour() {
        super(NO_TARGET);
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().plainString("End your turn").build();
    }

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match, Callback callback) {
        if (cardResolved.getCard().equals(this.getCard())) {
            logger.info("Ending the current turn");
            match.getController(MatchStateController.class).endTurn(callback::enqueue);
        }
    }

    @Override
    public EndTurnBehaviour duplicate() {
        return new EndTurnBehaviour();
    }
}
