package jrh.game.action;

import jrh.game.card.Card;
import jrh.game.match.CardFlowController;
import jrh.game.match.Match;
import jrh.game.match.Player;

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
