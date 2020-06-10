package jrh.game.card.event;

import jrh.game.api.Card;
import jrh.game.api.Damageable;
import jrh.game.api.Player;
import jrh.game.event.Event;

import java.util.Optional;

public class CardPlayed implements Event {

    private final Player player;
    private final Damageable target;
    private final Card card;

    public CardPlayed(Player player, Damageable target, Card card) {
        this.player = player;
        this.target = target;
        this.card = card;
    }

    public Player getPlayer() {
        return player;
    }

    public Optional<Damageable> getTarget() {
        return Optional.ofNullable(target);
    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getClass().getSimpleName(), card);
    }
}
