package jrh.game.event.impl;

import jrh.game.card.Card;
import jrh.game.event.Event;
import jrh.game.match.Player;

import java.util.Optional;

public class CardPlayed implements Event {

    private final Player player;
    private final Player target;
    private final Card card;

    public CardPlayed(Player player, Player target, Card card) {
        this.player = player;
        this.target = target;
        this.card = card;
    }

    public Player getPlayer() {
        return player;
    }

    public Optional<Player> getTarget() {
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
