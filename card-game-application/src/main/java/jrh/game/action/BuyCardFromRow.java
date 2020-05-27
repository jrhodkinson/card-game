package jrh.game.action;

import jrh.game.card.Card;
import jrh.game.match.CardFlowController;
import jrh.game.match.MutableMatch;
import jrh.game.match.MutablePlayer;

public class BuyCardFromRow implements Action {

    private final MutableMatch match;
    private final MutablePlayer player;
    private final Card card;

    public BuyCardFromRow(MutableMatch match, MutablePlayer player, Card card) {
        this.match = match;
        this.player = player;
        this.card = card;
    }

    @Override
    public void perform() {
        match.getController(CardFlowController.class).buyCardFromRow(player, card);
    }
}
