package jrh.game.api.action;

import jrh.game.api.Card;
import jrh.game.api.ControllableMatch;
import jrh.game.api.Damageable;
import jrh.game.api.Player;
import jrh.game.match.CardFlowController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayCard implements Action {

    private static final Logger logger = LogManager.getLogger(PlayCard.class);

    private final Player player;
    private final Card card;
    private final Damageable target;

    public PlayCard(Player player, Card card, Damageable target) {
        this.player = player;
        this.card = card;
        this.target = target;
    }

    @Override
    public void perform(ControllableMatch match) {
        match.getController(CardFlowController.class).playCard(player, card, target);
    }
}
