package jrh.game.match;

import jrh.game.card.Card;
import jrh.game.card.behaviour.UnplayableBehaviour;
import jrh.game.card.event.CardDestroyed;
import jrh.game.card.event.CardPlayed;
import jrh.game.card.event.CardResolved;
import jrh.game.deck.DiscardPile;
import jrh.game.match.api.Damageable;
import jrh.game.match.api.Player;
import jrh.game.match.event.CardPurchased;
import jrh.game.match.event.DiscardedCard;
import jrh.game.match.event.DrewCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CardFlowController implements Controller {

    private static final Logger logger = LogManager.getLogger(CardFlowController.class);
    private final MutableMatch match;

    public CardFlowController(MutableMatch match) {
        this.match = match;
    }

    public Optional<Player> getOwner(Card card) {
        if (match.getActivePlayer().getHand().contains(card)
                || match.getActivePlayer().getDeckAndDiscardPile().contains(card)) {
            return Optional.of(match.getActivePlayer());
        }
        if (match.getInactivePlayer().getHand().contains(card)
                || match.getInactivePlayer().getDeckAndDiscardPile().contains(card)) {
            return Optional.of(match.getActivePlayer());
        }
        return Optional.empty();
    }

    public void playCard(Player player, Card card, Damageable target) {
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
        Optional<Card> card = match.getPlayerAsMutable(player).drawToHand();
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
        int cost = match.getModificationComputer().computeModifiedCost(player, card);
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
        match.getEventBus().dispatch(new CardPurchased(match.getActivePlayer(), card));
    }

    public void buyCardFromPermanentPile(Player player, Card card) {
        if (!match.getActivePlayer().equals(player)) {
            logger.error("Player wasn't the active player");
            return;
        }
        int money = match.getCurrentTurn().getMoney();
        int cost = match.getModificationComputer().computeModifiedCost(player, card);
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
        match.getEventBus().dispatch(new CardPurchased(match.getActivePlayer(), card));
    }

    public void discardCard(Player player, Card card) {
        MutablePlayer mutablePlayer = match.getPlayerAsMutable(player);
        DiscardPile discardPile = mutablePlayer.getDeckAndDiscardPile().getDiscardPile();
        if (mutablePlayer.getHand().remove(card)) {
            discardPile.add(card);
            match.getEventBus().dispatch(new DiscardedCard(player, card));
        }
    }

    public void discardAllPlayedCards(Player player) {
        DiscardPile discardPile = match.getPlayerAsMutable(player).getDeckAndDiscardPile().getDiscardPile();
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
