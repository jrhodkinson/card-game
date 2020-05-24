package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.event.bus.Callback;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.CardPlayed;
import jrh.game.event.impl.CardResolved;
import jrh.game.match.Match;
import jrh.game.match.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IncrementingDamageBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(IncrementingDamageBehaviour.class);

    @JsonProperty
    private int damage;

    @JsonProperty
    private final int increment;

    public IncrementingDamageBehaviour(@JsonProperty("damage") int damage, @JsonProperty("increment") int increment) {
        this.damage = damage;
        this.increment = increment;
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match, Callback callback) {
        if (cardPlayed.getCard().equals(this.getCard()) && cardPlayed.getTarget().isPresent()) {
            Player target = cardPlayed.getTarget().get();
            logger.info("Damaging player={} by amount={}", target, damage);
            match.getHealthController().damage(target, this.damage);
        }
    }

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match, Callback callback) {
        if (cardResolved.getCard().equals(this.getCard())) {
            logger.info("Increasing damage of {} from {} by {}", this.getCard().getInstanceId(), damage, damage + increment);
            this.damage += increment;
        }
    }

    @Override
    public IncrementingDamageBehaviour duplicate() {
        return new IncrementingDamageBehaviour(damage, increment);
    }
}
