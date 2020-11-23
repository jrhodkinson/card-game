package jrh.game.match;

import jrh.game.api.Action;
import jrh.game.api.ActionHandler;
import jrh.game.api.Subscribe;
import jrh.game.api.action.BuyCard;
import jrh.game.api.action.EndTurn;
import jrh.game.api.action.PlayCard;
import jrh.game.common.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionHandlerImpl implements ActionHandler, EventHandler {

    private static final Logger logger = LogManager.getLogger(ActionHandlerImpl.class);

    private final MutableMatch match;

    ActionHandlerImpl(MutableMatch match) {
        this.match = match;
    }

    public void accept(Action action) {
        if (match.getActivePlayer().getUser().equals(action.getActor())) {
            match.getEventBus().dispatch(action);
        } else {
            logger.debug("Not playing action={} since actor was not the active user", action);
        }
    }

    @Subscribe
    private void buyCard(BuyCard buyCard) {
        match.getController(CardFlowController.class).buyCard(buyCard.getCardEntityId(), buyCard.getActor());
    }

    @Subscribe
    private void playCard(PlayCard playCard) {
        match.getController(CardFlowController.class).playCard(playCard.getActor(), playCard.getCardEntityId(),
                playCard.getTarget());
    }

    @Subscribe
    private void endTurn(EndTurn endTurn) {
        if (match.getActivePlayer().getUser().equals(endTurn.getActor())) {
            match.getController(MatchStateController.class).endTurn();
        }
    }
}
