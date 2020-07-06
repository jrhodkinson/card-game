package jrh.game.api.event;

import jrh.game.api.Card;
import jrh.game.api.Player;
import jrh.game.api.Event;

public class CardPurchased implements Event {

    private final Player purchaser;
    private final Card card;

    public CardPurchased(Player purchaser, Card card) {
        this.purchaser = purchaser;
        this.card = card;
    }

    public Player getPurchaser() {
        return purchaser;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getClass().getSimpleName(), card);
    }
}
