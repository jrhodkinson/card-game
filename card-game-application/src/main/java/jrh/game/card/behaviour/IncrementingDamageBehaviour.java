package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.card.event.CardPlayed;
import jrh.game.card.event.CardResolved;
import jrh.game.event.Callback;
import jrh.game.event.Subscribe;
import jrh.game.match.PlayerHealthController;
import jrh.game.match.api.Match;
import jrh.game.match.api.Player;
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
            match.getController(PlayerHealthController.class).damage(target, this.damage);
        }
    }

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match, Callback callback) {
        if (cardResolved.getCard().equals(this.getCard())) {
            logger.info("Increasing damage of {} from {} by {}", this.getCard().getInstanceId(), damage,
                    damage + increment);
            this.damage += increment;
        }
    }

    @Override
    public IncrementingDamageBehaviour duplicate() {
        return new IncrementingDamageBehaviour(damage, increment);
    }
}
