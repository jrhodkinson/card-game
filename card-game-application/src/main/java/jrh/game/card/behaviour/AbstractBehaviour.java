package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jrh.game.api.Behaviour;
import jrh.game.api.Card;
import jrh.game.common.description.AtomicDescription;

public abstract class AbstractBehaviour implements Behaviour {

    private final boolean requiresTarget;
    private Card card;

    protected AbstractBehaviour(boolean requiresTarget) {
        this.requiresTarget = requiresTarget;
    }

    public final void forCard(Card card) {
        this.card = card;
    }

    protected final Card getCard() {
        return card;
    }

    @Override
    public final boolean requiresTarget() {
        return requiresTarget;
    }

    @Override
    @JsonIgnore
    abstract public AtomicDescription getDescription();

    abstract public AbstractBehaviour duplicate();
}
