package jrh.game.action;

import jrh.game.api.Card;
import jrh.game.api.Match;
import jrh.game.api.Player;
import jrh.game.match.CardFlowController;

public class BuyCardFromPermanentPile implements Action {

    private final Match match;
    private final Player player;
    private final Card card;

    public BuyCardFromPermanentPile(Match match, Player player, Card card) {
        this.match = match;
        this.player = player;
        this.card = card;
    }

    @Override
    public void perform() {
        match.getController(CardFlowController.class).buyCardFromPermanentPile(player, card);
    }
}
