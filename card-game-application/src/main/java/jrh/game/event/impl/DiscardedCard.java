package jrh.game.event.impl;

import jrh.game.card.Card;
import jrh.game.event.Event;
import jrh.game.match.Match;
import jrh.game.match.Player;

public class DiscardedCard implements Event {

    private final Match match;
    private final Player player;
    private final Card card;

    public DiscardedCard(Match match, Player player, Card card) {
        this.match = match;
        this.player = player;
        this.card = card;
    }

    public Match getMatch() {
        return match;
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
