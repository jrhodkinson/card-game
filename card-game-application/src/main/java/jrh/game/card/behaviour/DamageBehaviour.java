package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.CardPlayed;
import jrh.game.match.Match;
import jrh.game.match.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DamageBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(DamageBehaviour.class);

    @JsonValue
    private final int damage;

    public DamageBehaviour(int damage) {
        this.damage = damage;
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard()) && cardPlayed.getTarget().isPresent()) {
            Player target = cardPlayed.getTarget().get();
            logger.info("Damaging player={} by amount={}", target, damage);
            match.getHealthController().damage(target, this.damage);
        }
    }

    @Override
    public DamageBehaviour duplicate() {
        return new DamageBehaviour(damage);
    }
}
