package jrh.game.api.event;

import jrh.game.api.Card;
import jrh.game.api.Event;
import jrh.game.api.Player;

public class CardPurged implements Event {

    private final Player purger;
    private final Card card;

    public CardPurged(Player purger, Card card) {
        this.purger = purger;
        this.card = card;
    }

    public Player getPurger() {
        return purger;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getClass().getSimpleName(), card);
    }
}
