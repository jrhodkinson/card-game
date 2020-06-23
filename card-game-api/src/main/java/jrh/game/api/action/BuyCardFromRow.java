package jrh.game.api.action;

import jrh.game.api.Card;
import jrh.game.api.ControllableMatch;
import jrh.game.api.Player;
import jrh.game.match.CardFlowController;

public class BuyCardFromRow implements Action {

    private final Player player;
    private final Card card;

    public BuyCardFromRow(Player player, Card card) {
        this.player = player;
        this.card = card;
    }

    @Override
    public void perform(ControllableMatch match) {
        match.getController(CardFlowController.class).buyCardFromRow(player, card);
    }
}
