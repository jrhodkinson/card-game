package jrh.game.match;

import jrh.game.event.EventHandler;
import jrh.game.event.bus.Callback;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.TurnEnded;
import jrh.game.event.impl.MatchEnded;
import jrh.game.event.impl.MatchStarted;
import jrh.game.event.impl.PlayerTookDamage;
import jrh.game.util.Constants;

import java.util.ArrayList;

public class MatchStateController implements EventHandler {

    private final Match match;

    public MatchStateController(Match match) {
        this.match = match;
    }

    public void startMatch() {
        CardFlowController cardFlowController = match.getCardFlowController();
        cardFlowController.drawCards(match.getActivePlayer(), Constants.INITIAL_HAND_SIZE);
        cardFlowController.drawCards(match.getInactivePlayer(), Constants.INITIAL_HAND_SIZE);
        match.getEventBus().dispatch(new MatchStarted());
    }

    public void endTurn() {
        Player activePlayer = match.getActivePlayer();
        CardFlowController cardFlowController = match.getCardFlowController();
        cardFlowController.discardAllPlayedCards(activePlayer);
        new ArrayList<>(activePlayer.getHand()).forEach(card -> cardFlowController.discardCard(activePlayer, card));
        cardFlowController.drawCards(activePlayer, Constants.INITIAL_HAND_SIZE - activePlayer.getHand().size());
        match.advanceToNextTurn();
        match.getEventBus().dispatch(new TurnEnded(match.getInactivePlayer(), match.getActivePlayer()));
    }

    @Subscribe
    private void targetTookDamage(PlayerTookDamage playerTookDamage, Match match, Callback callback) {
        if (playerTookDamage.getPlayer().getHealth() <= 0){
            match.end();
            callback.enqueue(new MatchEnded(match.getWinner().getUser()));
        }
    }
}
