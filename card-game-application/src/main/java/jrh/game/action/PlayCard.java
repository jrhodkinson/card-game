package jrh.game.action;

import jrh.game.Card;
import jrh.game.Match;
import jrh.game.Target;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayCard implements Action {

    private static final Logger logger = LogManager.getLogger(PlayCard.class);

    private final Card card;
    private final Target target;

    public PlayCard(Card card, Target target) {
        this.card = card;
        this.target = target;
    }

    @Override
    public void applyTo(Match match) {
        logger.info("Playing card={}", card);
        if (!match.getCurrentTurn().getActivePlayer().getHand().removeCard(card)) {
            logger.error("Player's hand did not contain card");
            return;
        }
        card.play(match, target);
    }
}
