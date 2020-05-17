package jrh.game.match;

import jrh.game.card.Card;
import jrh.game.deck.DiscardPile;
import jrh.game.event.EventHandler;
import jrh.game.event.bus.EventBus;
import jrh.game.event.impl.DiscardedCard;
import jrh.game.event.impl.DrewCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CardFlowManager implements EventHandler {

    private static final Logger logger = LogManager.getLogger(CardFlowManager.class);
    private final EventBus eventBus;
    private final Match match;

    public CardFlowManager(EventBus eventBus, Match match) {
        this.eventBus = eventBus;
        this.match = match;
    }

    public void drawCard(Player player) {
        Optional<Card> card = player.drawToHand();
        card.ifPresent(value -> eventBus.dispatch(new DrewCard(match, player, value)));
    }

    public void drawCards(Player player, int numberToDraw) {
        for (int i = 0; i < numberToDraw; i++) {
            drawCard(player);
        }
    }

    public void discardCard(Player player, Card card) {
        DiscardPile discardPile = player.getDeckAndDiscardPile().getDiscardPile();
        if (player.getHand().remove(card)) {
            discardPile.add(card);
            eventBus.dispatch(new DiscardedCard(match, player, card));
        }
    }

    public void discardAllPlayedCards(Player player) {
        DiscardPile discardPile = player.getDeckAndDiscardPile().getDiscardPile();
        if (match.getActivePlayer().equals(player)) {
            discardPile.addAll(match.getCurrentTurn().getPlayedCards());
            match.getCurrentTurn().clearPlayedCards();
        }
    }
}
