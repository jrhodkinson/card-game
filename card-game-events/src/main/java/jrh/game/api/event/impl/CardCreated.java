package jrh.game.api.event.impl;

import jrh.game.api.Card;
import jrh.game.api.event.Event;

public class CardCreated implements Event {

    private final Card card;

    public CardCreated(Card card) {
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
