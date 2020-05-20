package jrh.game.card.behaviour;

import jrh.game.card.Card;
import jrh.game.event.EventHandler;

public abstract class Behaviour implements EventHandler {

    private Card card;

    public final void forCard(Card card) {
        this.card = card;
    }

    protected final Card getCard() {
        return card;
    }

    abstract public Behaviour duplicate();
}
