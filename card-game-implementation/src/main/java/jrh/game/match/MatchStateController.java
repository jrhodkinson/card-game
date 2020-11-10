package jrh.game.match;

import jrh.game.api.Controller;
import jrh.game.api.Callback;
import jrh.game.api.event.TurnStarted;
import jrh.game.common.EventHandler;
import jrh.game.api.Subscribe;
import jrh.game.api.event.MatchEnded;
import jrh.game.api.event.MatchStarted;
import jrh.game.api.event.PlayerTookDamage;
import jrh.game.api.event.TurnEnded;
import jrh.game.api.Match;
import jrh.game.Constants;

import java.util.ArrayList;

public class MatchStateController implements Controller, EventHandler {

    private final MutableMatch match;

    public MatchStateController(MutableMatch match) {
        this.match = match;
    }

    @Override
    public void initialise() {
        match.getEventBus().register(this);
    }

    public void startMatch() {
        CardFlowController cardFlowController = match.getController(CardFlowController.class);
        cardFlowController.drawCards(match.getActivePlayer(), Constants.INITIAL_HAND_SIZE);
        cardFlowController.drawCards(match.getInactivePlayer(), Constants.INITIAL_HAND_SIZE);
        match.getEventBus().dispatch(new MatchStarted());
    }

    public void endTurn() {
        MutablePlayer activePlayer = match.getActivePlayer();
        CardFlowController cardFlowController = match.getController(CardFlowController.class);
        cardFlowController.discardAllPlayedCards(activePlayer);
        new ArrayList<>(activePlayer.getHand()).forEach(card -> cardFlowController.discardCard(activePlayer, card));
        cardFlowController.drawCards(activePlayer, Constants.INITIAL_HAND_SIZE - activePlayer.getHand().size());
        match.advanceToNextTurn();
        match.getEventBus().dispatch(new TurnEnded(match.getInactivePlayer(), match.getActivePlayer()));
        match.getEventBus().dispatch(new TurnStarted(match.getActivePlayer()));
    }

    @Subscribe
    private void targetTookDamage(PlayerTookDamage playerTookDamage, Match match, Callback callback) {
        if (playerTookDamage.getPlayer().getHealth() <= 0) {
            this.match.end();
            callback.enqueue(new MatchEnded(this.match.getWinner().getUser()));
        }
    }
}
