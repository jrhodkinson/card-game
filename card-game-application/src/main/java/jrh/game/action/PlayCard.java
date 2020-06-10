package jrh.game.action;

import jrh.game.api.Card;
import jrh.game.api.Damageable;
import jrh.game.api.Match;
import jrh.game.api.Player;
import jrh.game.match.CardFlowController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayCard implements Action {

    private static final Logger logger = LogManager.getLogger(PlayCard.class);

    private final Match match;
    private final Player player;
    private final Card card;
    private final Damageable target;

    public PlayCard(Match match, Player player, Card card, Damageable target) {
        this.match = match;
        this.player = player;
        this.card = card;
        this.target = target;
    }

    @Override
    public void perform() {
        match.getController(CardFlowController.class).playCard(player, card, target);
    }
}
