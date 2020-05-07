package jrh.game.action;

import jrh.game.Card;
import jrh.game.Match;
import jrh.game.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayCard implements Action {

    private static final Logger logger = LogManager.getLogger(PlayCard.class);

    private final Card card;

    public PlayCard(Card card) {
        this.card = card;
    }

    @Override
    public void applyTo(Match match) {
        logger.info("Playing card={}", card);
        Player currentPlayer = match.currentPlayer();
        if (!currentPlayer.getHand().removeCard(card)) {
            logger.error("Player's hand did not contain card");
        }
        logger.warn("Not implemented yet");
    }
}
