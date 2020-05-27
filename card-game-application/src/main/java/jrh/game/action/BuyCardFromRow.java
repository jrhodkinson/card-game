package jrh.game.action;

import jrh.game.card.Card;
import jrh.game.match.CardFlowController;
import jrh.game.match.api.Match;
import jrh.game.match.api.Player;

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
        match.getController(CardFlowController.class).buyCardFromRow(player, card);
    }
}
