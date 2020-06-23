package jrh.game.api.action;

import jrh.game.api.Card;
import jrh.game.api.Damageable;
import jrh.game.api.Player;

public class PlayCard implements Action {

    private final Player player;
    private final Card card;
    private final Damageable target;

    public PlayCard(Player player, Card card, Damageable target) {
        this.player = player;
        this.card = card;
        this.target = target;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }

    public Damageable getTarget() {
        return target;
    }
}
