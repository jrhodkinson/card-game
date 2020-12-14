package jrh.game.api.event;

import jrh.game.api.Card;
import jrh.game.api.Damageable;
import jrh.game.api.Event;
import jrh.game.api.Player;
import jrh.game.common.EntityId;

import java.util.Optional;

public class CardPlayed implements Event {

    private final Type type;
    private final Player player;
    private final Card card;
    private final Damageable damageableTarget;
    private final EntityId storeTarget;

    public static CardPlayed noTarget(Player player, Card card) {
        return new CardPlayed(Type.NO_TARGET, player, card, null, null);
    }

    public static CardPlayed damageableTarget(Player player, Card card, Damageable target) {
        return new CardPlayed(Type.DAMAGEABLE_TARGET, player, card, target, null);
    }

    public static CardPlayed storeTarget(Player player, Card card, EntityId cardEntity) {
        return new CardPlayed(Type.STORE_TARGET, player, card, null, cardEntity);
    }

    private CardPlayed(Type type, Player player, Card card, Damageable damageableTarget, EntityId storeTarget) {
        this.type = type;
        this.player = player;
        this.card = card;
        this.damageableTarget = damageableTarget;
        this.storeTarget = storeTarget;
    }

    public Player getPlayer() {
        return player;
    }

    public Optional<Damageable> getDamageableTarget() {
        if (type != Type.DAMAGEABLE_TARGET) {
            return Optional.empty();
        }
        return Optional.of(damageableTarget);
    }

    public Optional<EntityId> getStoreTarget() {
        if (type != Type.STORE_TARGET) {
            return Optional.empty();
        }
        return Optional.of(storeTarget);
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getClass().getSimpleName(), card);
    }

    private enum Type {
        NO_TARGET, DAMAGEABLE_TARGET, STORE_TARGET
    }
}
