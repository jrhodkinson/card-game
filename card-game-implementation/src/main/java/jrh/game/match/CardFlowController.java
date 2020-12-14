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
import jrh.game.structure.StructureStateController;
import jrh.game.structure.power.TauntPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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
        CardPlayed cardPlayed;
        if (card.requiresDamageableTarget()) {
            Damageable damageable = getDamageableTarget(target);
            if (!validateDamageableTarget(card, damageable, player)) {
                logger.info("Damageable failed validation");
                return;
            }
            cardPlayed = CardPlayed.damageableTarget(player, card, damageable);
        } else if (card.requiresStoreTarget()) {
            if (!validateStoreTarget(card, target)) {
                logger.info("Store target failed validation");
                return;
            }
            cardPlayed = CardPlayed.storeTarget(player, card, target);
        } else {
            cardPlayed = CardPlayed.noTarget(player, card);
        }
        player.getHand().remove(card);
        match.getEventBus().dispatch(cardPlayed);
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

    public void destroyStoreCard(EntityId entityId) {
        Optional<Card> optionalCard = match.getStore().getRow().stream().filter(c -> c.getEntityId().equals(entityId))
                .findFirst();
        if (optionalCard.isPresent()) {
            Card card = optionalCard.get();
            match.getStore().removeFromRow(card);
            match.getEventBus().dispatch(new CardDestroyed(card));
        } else {
            logger.info("Could not destroy card with entityId={}, it wasn't in the store", entityId);
        }
    }

    public void destroyPlayedCard(Card card) {
        match.getCurrentTurn().removePlayedCard(card);
        match.getEventBus().dispatch(new CardDestroyed(card));
    }

    private Damageable getDamageableTarget(EntityId target) {
        if (target == null) {
            return null;
        }
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

    private boolean validateDamageableTarget(Card card, Damageable damageable, Player player) {
        if (damageable == null) {
            logger.warn("Not playing card={}, couldn't find damageable target", card);
            return false;
        }
        if (anotherEnemyStructureHasTaunt(player, damageable)) {
            logger.warn("Not playing card={}, at least one other enemy structure has taunt", card);
            return false;
        }
        return true;
    }

    private boolean validateStoreTarget(Card card, EntityId entityId) {
        if (entityId == null) {
            logger.warn("Not playing card={}, couldn't find storefront target", card);
            return false;
        }
        return match.getStore().getRow().stream().anyMatch(c -> c.getEntityId().equals(entityId));
    }

    private boolean anotherEnemyStructureHasTaunt(Player source, Damageable target) {
        if (target.equals(source)) {
            return false;
        }
        Player otherPlayer = match.getOtherPlayer(source.getUser());
        StructureStateController structureStateController = match.getController(StructureStateController.class);
        if (target instanceof Structure && structureStateController.getOwner((Structure) target).equals(source)) {
            return false;
        }
        List<EntityId> otherPlayersStructuresWithTaunt = structuresWithTaunt().stream()
                .filter(s -> structureStateController.getOwner(s).equals(otherPlayer)).map(Structure::getEntityId)
                .collect(toList());
        if (otherPlayersStructuresWithTaunt.isEmpty()) {
            return false;
        }
        return !otherPlayersStructuresWithTaunt.contains(target.getEntityId());
    }

    private List<Structure> structuresWithTaunt() {
        return match.getAllStructures().stream().filter(s -> s.hasPower(TauntPower.class)).collect(toList());
    }
}
