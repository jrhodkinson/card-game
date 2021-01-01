package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jrh.game.api.Behaviour;
import jrh.game.api.Card;
import jrh.game.common.description.AtomicDescription;

import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.DAMAGEABLE;
import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.HAND;
import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.STORE;

public abstract class AbstractBehaviour implements Behaviour {

    private final TargetType targetType;
    private Card card;

    protected AbstractBehaviour(TargetType targetType) {
        this.targetType = targetType;
    }

    public final void forCard(Card card) {
        this.card = card;
    }

    protected final Card getCard() {
        return card;
    }

    @Override
    public final boolean requiresDamageableTarget() {
        return DAMAGEABLE.equals(targetType);
    }

    @Override
    public final boolean requiresStoreTarget() {
        return STORE.equals(targetType);
    }

    @Override
    public final boolean requiresCardInHandTarget() {
        return HAND.equals(targetType);
    }

    @Override
    @JsonIgnore
    abstract public AtomicDescription getDescription();

    abstract public AbstractBehaviour duplicate();

    protected enum TargetType {
        NO_TARGET, DAMAGEABLE, STORE, HAND
    }
}
