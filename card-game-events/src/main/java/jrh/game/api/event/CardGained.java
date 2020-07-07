package jrh.game.api.event;

import jrh.game.api.Card;
import jrh.game.api.Player;
import jrh.game.api.Event;

public class CardGained implements Event {

    private final Player gainer;
    private final Card card;

    public CardGained(Player gainer, Card card) {
        this.gainer = gainer;
        this.card = card;
    }

    public Player getGainer() {
        return gainer;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getClass().getSimpleName(), card);
    }
}
