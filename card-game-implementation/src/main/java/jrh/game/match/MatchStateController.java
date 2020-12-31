package jrh.game.match;

import jrh.game.Constants;
import jrh.game.api.Callback;
import jrh.game.api.Controller;
import jrh.game.api.Event;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.MatchEnded;
import jrh.game.api.event.MatchStarted;
import jrh.game.api.event.PlayerRanOutOfTime;
import jrh.game.api.event.PlayerTookDamage;
import jrh.game.api.event.TurnEnded;
import jrh.game.api.event.TurnStarted;
import jrh.game.common.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.function.Consumer;

public class MatchStateController implements Controller, EventHandler {

    private static final Logger logger = LogManager.getLogger(MatchStateController.class);

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
        cardFlowController.giveSecondPlayerBonus(match.getInactivePlayer().getUser());
        match.getEventBus().dispatch(new MatchStarted());
        match.getEventBus().dispatch(new TurnStarted(Instant.now(), match.getActivePlayer()));
    }

    public void endTurn(Consumer<Event> eventConsumer) {
        doEndTurn(eventConsumer);
    }

    @Subscribe
    private void playerRanOutOfTime(PlayerRanOutOfTime playerRanOutOfTime, Match match, Callback callback) {
        if (match.getActivePlayer().equals(playerRanOutOfTime.getPlayer())) {
            logger.info("Player={} ran out of time, ending their turn", playerRanOutOfTime.getPlayer());
            doEndTurn(callback::enqueue);
        }
    }

    @Subscribe
    private void targetTookDamage(PlayerTookDamage playerTookDamage, Match match, Callback callback) {
        if (playerTookDamage.getPlayer().getHealth() <= 0) {
            this.match.end();
            callback.enqueue(new MatchEnded(this.match.getWinner().getUser()));
        }
    }

    private void doEndTurn(Consumer<Event> eventConsumer) {
        MutablePlayer activePlayer = match.getActivePlayer();
        logger.info("Ending the player={} turn", activePlayer);
        CardFlowController cardFlowController = match.getController(CardFlowController.class);
        cardFlowController.discardAllPlayedCards(activePlayer);
        new ArrayList<>(activePlayer.getHand()).forEach(card -> cardFlowController.discardCard(activePlayer, card));
        cardFlowController.drawCards(activePlayer, Constants.INITIAL_HAND_SIZE - activePlayer.getHand().size());
        match.advanceToNextTurn();
        eventConsumer.accept(new TurnEnded(match.getInactivePlayer(), match.getActivePlayer()));
        eventConsumer.accept(new TurnStarted(Instant.now(), match.getActivePlayer()));
    }
}
