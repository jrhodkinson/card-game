package jrh.game.action;

import jrh.game.card.Card;
import jrh.game.match.Match;
import jrh.game.match.Player;
import jrh.game.match.Target;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayCard implements Action {

    private static final Logger logger = LogManager.getLogger(PlayCard.class);

    private final Match match;
    private final Player player;
    private final Card card;
    private final Target target;

    public PlayCard(Match match, Player player, Card card, Target target) {
        this.match = match;
        this.player = player;
        this.card = card;
        this.target = target;
    }

    @Override
    public void perform() {
        match.getCardFlowManager().playCard(player, card, target);
    }
}
