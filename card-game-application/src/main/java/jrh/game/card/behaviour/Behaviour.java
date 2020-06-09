package jrh.game.card.behaviour;

import jrh.game.card.Card;
import jrh.game.event.EventHandler;

public abstract class Behaviour implements EventHandler {

    private final boolean requiresTarget;
    private Card card;

    protected Behaviour(boolean requiresTarget) {
        this.requiresTarget = requiresTarget;
    }

    public final void forCard(Card card) {
        this.card = card;
    }

    protected final Card getCard() {
        return card;
    }

    public final boolean requiresTarget() {
        return requiresTarget;
    }

    abstract public BehaviourDescription getDescription();

    abstract public Behaviour duplicate();
}
