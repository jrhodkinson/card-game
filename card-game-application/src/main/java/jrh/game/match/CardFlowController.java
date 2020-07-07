package jrh.game.match;

import jrh.game.api.Card;
import jrh.game.api.Controller;
import jrh.game.api.Damageable;
import jrh.game.api.Player;
import jrh.game.api.Structure;
import jrh.game.api.event.CardDestroyed;
import jrh.game.api.event.CardGained;
import jrh.game.api.event.CardPlayed;
import jrh.game.api.event.CardPurchased;
import jrh.game.api.event.CardResolved;
import jrh.game.api.event.DiscardedCard;
import jrh.game.api.event.DrewCard;
import jrh.game.card.CardImpl;
import jrh.game.card.CardImplFactory;
import jrh.game.card.behaviour.UnplayableBehaviour;
import jrh.game.common.CardId;
import jrh.game.common.EntityId;
import jrh.game.common.User;
import jrh.game.deck.DiscardPile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CardFlowController implements Controller {

    private static final Logger logger = LogManager.getLogger(CardFlowController.class);
    private final MutableMatch match;
    private final CardImplFactory cardImplFactory;

    public CardFlowController(MutableMatch match, CardImplFactory cardImplFactory) {
        this.match = match;
        this.cardImplFactory = cardImplFactory;
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

    public void playCard(User user, EntityId cardEntityId, EntityId target) {
        if (!match.getActivePlayer().getUser().equals(user)) {
            logger.warn("Not playing cardInstanceId={}, user={} wasn't the active player", cardEntityId, user);
            return;
        }
        MutablePlayer player = match.getPlayer(user);
        Optional<Card> optionalCard = player.getHand().getCard(cardEntityId);
        if (optionalCard.isEmpty()) {
            logger.warn("Not playing cardInstanceId={}, user={} hand did not contain card", cardEntityId, user);
            return;
        }
        Card card = optionalCard.get();
        if (card.hasBehaviour(UnplayableBehaviour.class)) {
            logger.info("Not playing card={}, it has UnplayableBehaviour", card);
            return;
        }
        Damageable damageable = null;
        if (target != null) {
            damageable = getDamageableTarget(target);
            if (damageable == null) {
                logger.warn("Not playing card={}, target={} was not damageable", card, target);
                return;
            }
        }
        player.getHand().remove(card);
        match.getEventBus().dispatch(new CardPlayed(player, damageable, card));
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

    public void buyCard(EntityId cardEntityId, User user) {
        if (!match.getActivePlayer().getUser().equals(user)) {
            logger.warn("Not buying card, user={} wasn't the active player", user);
            return;
        }
        Optional<Card> optionalCard = match.getStore().getCard(cardEntityId);
        if (optionalCard.isEmpty()) {
            logger.warn("Not buying card, cardInstanceId={} wasn't in the store", cardEntityId);
            return;
        }
        Card card = optionalCard.get();
        Player player = match.getPlayer(user);
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
        match.getEventBus().dispatch(new CardGained(match.getActivePlayer(), card));
    }

    public void gainDirectly(CardId cardId, User user) {
        Optional<CardImpl> optionalCard = cardImplFactory.create(cardId);
        if (optionalCard.isEmpty()) {
            logger.error("Could not gain card with cardId={} for user={}. The factory didn't create one", cardId, user);
            return;
        }
        CardImpl card = optionalCard.get();
        match.getActivePlayer().getDeckAndDiscardPile().getDiscardPile().add(card);
        match.getEventBus().dispatch(new CardGained(match.getActivePlayer(), card));
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

    private Damageable getDamageableTarget(EntityId target) {
        Optional<Structure> optionalStructure = match.getAllStructures().stream()
                .filter(structure -> structure.getEntityId().equals(target)).findFirst();
        if (optionalStructure.isPresent()) {
            return optionalStructure.get();
        }
        if (match.getActivePlayer().getEntityId().equals(target)) {
            return match.getActivePlayer();
        }
        if (match.getInactivePlayer().getEntityId().equals(target)) {
            return match.getInactivePlayer();
        }
        return null;
    }
}
