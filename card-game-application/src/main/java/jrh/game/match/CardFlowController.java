package jrh.game.match;

import jrh.game.card.Card;
import jrh.game.card.behaviour.UnplayableBehaviour;
import jrh.game.deck.DiscardPile;
import jrh.game.event.impl.CardBought;
import jrh.game.event.impl.CardDestroyed;
import jrh.game.event.impl.CardPlayed;
import jrh.game.event.impl.CardResolved;
import jrh.game.event.impl.DiscardedCard;
import jrh.game.event.impl.DrewCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CardFlowController {

    private static final Logger logger = LogManager.getLogger(CardFlowController.class);
    private final Match match;

    public CardFlowController(Match match) {
        this.match = match;
    }

    public void playCard(Player player, Card card, Player target) {
        if (!match.getActivePlayer().equals(player)) {
            logger.error("Player wasn't the active player");
            return;
        }
        if (card.hasBehaviour(UnplayableBehaviour.class)) {
            logger.error("Card has UnplayableBehaviour");
            return;
        }
        if (!match.getActivePlayer().getHand().remove(card)) {
            logger.error("Player's hand did not contain card");
            return;
        }
        match.getEventBus().dispatch(new CardPlayed(player, target, card));
        match.getCurrentTurn().addPlayedCard(card);
        match.getEventBus().dispatch(new CardResolved(player, card));
    }

    public void drawCard(Player player) {
        Optional<Card> card = player.drawToHand();
        card.ifPresent(value -> match.getEventBus().dispatch(new DrewCard(player, value)));
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
        match.getEventBus().dispatch(new CardBought(match.getActivePlayer(), card));
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
        match.getEventBus().dispatch(new CardBought(match.getActivePlayer(), card));
    }

    public void discardCard(Player player, Card card) {
        DiscardPile discardPile = player.getDeckAndDiscardPile().getDiscardPile();
        if (player.getHand().remove(card)) {
            discardPile.add(card);
            match.getEventBus().dispatch(new DiscardedCard(player, card));
        }
    }

    public void discardAllPlayedCards(Player player) {
        DiscardPile discardPile = player.getDeckAndDiscardPile().getDiscardPile();
        if (match.getActivePlayer().equals(player)) {
            discardPile.addAll(match.getCurrentTurn().getPlayedCards());
            match.getCurrentTurn().clearPlayedCards();
        }
    }

    public void destroyPlayedCard(Card card) {
        match.getCurrentTurn().removePlayedCard(card);
        match.getEventBus().dispatch(new CardDestroyed(card));
    }
}
