package jrh.game.action;

import jrh.game.card.Card;
import jrh.game.match.Match;
import jrh.game.match.Player;

public class BuyCardFromRow implements Action {

    private final Match match;
    private final Player player;
    private final Card card;

    public BuyCardFromRow(Match match, Player player, Card card) {
        this.match = match;
        this.player = player;
        this.card = card;
    }

    @Override
    public void perform() {
        match.getCardFlowManager().buyCardFromRow(player, card);
    }
}
