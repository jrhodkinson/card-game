package jrh.game.match;

import jrh.game.event.EventHandler;
import jrh.game.event.bus.Callback;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.AdvancedToNextTurn;
import jrh.game.event.impl.EndedTurn;
import jrh.game.event.impl.StartedMatch;
import jrh.game.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MatchFlowHandler implements EventHandler {

    private static final Logger logger = LogManager.getLogger(MatchFlowHandler.class);

    @Subscribe
    private void startedMatch(StartedMatch startedMatch) {
        logger.info("RX event={}", startedMatch);
        Match match = startedMatch.getMatch();
        CardFlowManager cardFlowManager = match.getCardFlowManager();
        cardFlowManager.drawCards(match.getActivePlayer(), Constants.INITIAL_HAND_SIZE);
        cardFlowManager.drawCards(match.getInactivePlayer(), Constants.INITIAL_HAND_SIZE);
    }

    @Subscribe
    private void endedTurn(EndedTurn endedTurn, Callback callback) {
        logger.info("RX event={}", endedTurn);
        Match match = endedTurn.getMatch();
        Player activePlayer = match.getActivePlayer();
        CardFlowManager cardFlowManager = match.getCardFlowManager();
        cardFlowManager.discardAllPlayedCards(activePlayer);
        new ArrayList<>(activePlayer.getHand()).forEach(card -> cardFlowManager.discardCard(activePlayer, card));
        cardFlowManager.drawCards(activePlayer, Constants.INITIAL_HAND_SIZE - activePlayer.getHand().size());
        match.advanceToNextTurn();
        callback.enqueue(new AdvancedToNextTurn(match));
    }
}
