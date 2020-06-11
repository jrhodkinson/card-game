package jrh.game.api.event.impl;

import jrh.game.api.Card;
import jrh.game.api.event.Event;

public class CardDestroyed implements Event {

    private final Card card;

    public CardDestroyed(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getClass().getSimpleName(), card);
    }
}
