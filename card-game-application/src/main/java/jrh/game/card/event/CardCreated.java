package jrh.game.card.event;

import jrh.game.card.Card;
import jrh.game.event.Event;

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
