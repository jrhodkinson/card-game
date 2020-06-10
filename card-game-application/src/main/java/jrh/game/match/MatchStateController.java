package jrh.game.match;

import jrh.game.api.Controller;
import jrh.game.event.Callback;
import jrh.game.common.EventHandler;
import jrh.game.event.Subscribe;
import jrh.game.match.event.MatchEnded;
import jrh.game.match.event.MatchStarted;
import jrh.game.match.event.PlayerTookDamage;
import jrh.game.match.event.TurnEnded;
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
    }

    @Subscribe
    private void targetTookDamage(PlayerTookDamage playerTookDamage, Match match, Callback callback) {
        if (playerTookDamage.getPlayer().getHealth() <= 0) {
            this.match.end();
            callback.enqueue(new MatchEnded(this.match.getWinner().getUser()));
        }
    }
}
