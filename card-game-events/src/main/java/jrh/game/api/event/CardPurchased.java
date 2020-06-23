package jrh.game.api.event;

import jrh.game.api.Card;
import jrh.game.api.Player;
import jrh.game.api.event.Event;

public class CardPurchased implements Event {

    private final Player player;
    private final Card card;

    public CardPurchased(Player player, Card card) {
        this.player = player;
        this.card = card;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getClass().getSimpleName(), card);
    }
}
