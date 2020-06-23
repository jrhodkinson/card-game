package jrh.game.match;

import jrh.game.api.Action;
import jrh.game.api.ActionHandler;
import jrh.game.api.Subscribe;
import jrh.game.api.action.BuyCardFromPermanentPile;
import jrh.game.api.action.BuyCardFromRow;
import jrh.game.api.action.EndTurn;
import jrh.game.api.action.PlayCard;
import jrh.game.common.EventHandler;

public class ActionHandlerImpl implements ActionHandler, EventHandler {

    private final MutableMatch match;

    ActionHandlerImpl(MutableMatch match) {
        this.match = match;
    }

    public void accept(Action action) {
        match.getEventBus().dispatch(action);
    }

    @Subscribe
    private void buyCardFromRow(BuyCardFromRow buyCardFromRow) {
        match.getController(CardFlowController.class).buyCardFromRow(buyCardFromRow.getPlayer(), buyCardFromRow.getCard());
    }

    @Subscribe
    private void buyCardFromPermanentPile(BuyCardFromPermanentPile buyCardFromPermanentPile) {
        match.getController(CardFlowController.class).buyCardFromPermanentPile(buyCardFromPermanentPile.getPlayer(), buyCardFromPermanentPile.getCard());
    }

    @Subscribe
    private void playCard(PlayCard playCard) {
        match.getController(CardFlowController.class).playCard(playCard.getUser(), playCard.getCardInstanceId(), playCard.getTarget());
    }

    @Subscribe
    private void endTurn(EndTurn endTurn) {
        if (match.getActivePlayer().getUser().equals(endTurn.getUser())) {
            match.getController(MatchStateController.class).endTurn();
        }
    }
}
