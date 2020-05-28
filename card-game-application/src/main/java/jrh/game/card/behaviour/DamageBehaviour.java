package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.card.event.CardPlayed;
import jrh.game.event.Subscribe;
import jrh.game.match.HealthController;
import jrh.game.match.api.Damageable;
import jrh.game.match.api.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DamageBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(DamageBehaviour.class);

    @JsonValue
    private final int damage;

    public DamageBehaviour(int damage) {
        super(true);
        this.damage = damage;
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard()) && cardPlayed.getTarget().isPresent()) {
            Damageable target = cardPlayed.getTarget().get();
            logger.info("Damaging target={} by amount={}", target, damage);
            match.getController(HealthController.class).damage(target, this.damage);
        }
    }

    @Override
    public DamageBehaviour duplicate() {
        return new DamageBehaviour(damage);
    }
}
