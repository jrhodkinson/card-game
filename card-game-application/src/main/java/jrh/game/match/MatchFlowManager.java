package jrh.game.match;

import jrh.game.event.impl.EndedTurn;
import jrh.game.event.impl.StartedMatch;
import jrh.game.util.Constants;

import java.util.ArrayList;

public class MatchFlowManager {

    private final Match match;

    public MatchFlowManager(Match match) {
        this.match = match;
    }

    public void startMatch() {
        CardFlowManager cardFlowManager = match.getCardFlowManager();
        cardFlowManager.drawCards(match.getActivePlayer(), Constants.INITIAL_HAND_SIZE);
        cardFlowManager.drawCards(match.getInactivePlayer(), Constants.INITIAL_HAND_SIZE);
        match.getEventBus().dispatch(new StartedMatch());
    }

    public void endTurn() {
        Player activePlayer = match.getActivePlayer();
        CardFlowManager cardFlowManager = match.getCardFlowManager();
        cardFlowManager.discardAllPlayedCards(activePlayer);
        new ArrayList<>(activePlayer.getHand()).forEach(card -> cardFlowManager.discardCard(activePlayer, card));
        cardFlowManager.drawCards(activePlayer, Constants.INITIAL_HAND_SIZE - activePlayer.getHand().size());
        match.advanceToNextTurn();
        match.getEventBus().dispatch(new EndedTurn());
    }
}
