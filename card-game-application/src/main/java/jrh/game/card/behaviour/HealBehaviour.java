package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.event.Subscribe;
import jrh.game.card.event.CardPlayed;
import jrh.game.match.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HealBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(HealBehaviour.class);

    @JsonValue
    private final int health;

    public HealBehaviour(int health) {
        this.health = health;
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard()) && cardPlayed.getTarget().isPresent()) {
            logger.info("Increasing health of {} by {}", cardPlayed.getTarget().get(), this.health);
            match.getPlayerHealthController().heal(cardPlayed.getTarget().get(), this.health);
        }
    }

    @Override
    public HealBehaviour duplicate() {
        return new HealBehaviour(health);
    }
}
