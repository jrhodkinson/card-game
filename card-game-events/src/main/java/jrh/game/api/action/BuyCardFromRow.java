package jrh.game.api.action;

import jrh.game.api.Card;
import jrh.game.api.Player;

public class BuyCardFromRow implements Action {

    private final Player player;
    private final Card card;

    public BuyCardFromRow(Player player, Card card) {
        this.player = player;
        this.card = card;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }
}
