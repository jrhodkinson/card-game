package jrh.game.action;

import jrh.game.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EndTurn implements Action {

    private static final Logger logger = LogManager.getLogger(EndTurn.class);

    @Override
    public void applyTo(Match match) {
        logger.info("Ending turn");
        match.advanceToNextTurn();
    }
}
