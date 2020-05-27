package jrh.game.action;

import jrh.game.card.Card;
import jrh.game.match.CardFlowController;
import jrh.game.match.MutableMatch;
import jrh.game.match.MutablePlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayCard implements Action {

    private static final Logger logger = LogManager.getLogger(PlayCard.class);

    private final MutableMatch match;
    private final MutablePlayer player;
    private final Card card;
    private final MutablePlayer target;

    public PlayCard(MutableMatch match, MutablePlayer player, Card card, MutablePlayer target) {
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
