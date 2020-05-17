package jrh.game.match;

import jrh.game.card.Card;
import jrh.game.deck.DiscardPile;
import jrh.game.event.EventHandler;
import jrh.game.event.bus.EventBus;
import jrh.game.event.impl.DiscardedCard;
import jrh.game.event.impl.DrewCard;
import jrh.game.event.impl.PlayedCard;
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

    public void playCard(Player player, Card card, Target target) {
        if (!match.getActivePlayer().equals(player)) {
            logger.error("Player wasn't the active player");
            return;
        }
        if (!match.getActivePlayer().getHand().remove(card)) {
            logger.error("Player's hand did not contain card");
            return;
        }
        card.play(match, target);
        match.getCurrentTurn().addPlayedCard(card);
        eventBus.dispatch(new PlayedCard(match, player, card));
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

    public void buyCardFromRow(Player player, Card card) {
        if (!match.getActivePlayer().equals(player)) {
            logger.error("Player wasn't the active player");
            return;
        }
        int money = match.getCurrentTurn().getMoney();
        int cost = card.getCost();
        if (money < cost) {
            logger.error("Not enough money to buy card (money={}, cost={})", money, cost);
            return;
        }
        if (!match.getStore().removeFromRow(card)) {
            logger.error("Card={} is not in the store row", card);
            return;
        }
        match.getCurrentTurn().setMoney(money - cost);
        match.getActivePlayer().getDeckAndDiscardPile().getDiscardPile().add(card);
    }

    public void buyCardFromPermanentPile(Player player, Card card) {
        if (!match.getActivePlayer().equals(player)) {
            logger.error("Player wasn't the active player");
            return;
        }
        int money = match.getCurrentTurn().getMoney();
        int cost = card.getCost();
        if (money < cost) {
            logger.error("Not enough money to buy card (money={}, cost={})", money, cost);
            return;
        }
        if (!match.getStore().removeFromPile(card)) {
            logger.error("Card={} is not in a pile", card);
            return;
        }
        match.getCurrentTurn().setMoney(money - cost);
        match.getActivePlayer().getDeckAndDiscardPile().getDiscardPile().add(card);
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
