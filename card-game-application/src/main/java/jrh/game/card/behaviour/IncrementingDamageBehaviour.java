package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.asset.JsonKey;
import jrh.game.card.event.CardPlayed;
import jrh.game.card.event.CardResolved;
import jrh.game.common.BehaviourDescription;
import jrh.game.event.Callback;
import jrh.game.event.Subscribe;
import jrh.game.match.HealthController;
import jrh.game.api.Damageable;
import jrh.game.api.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("incrementing-damage")
public class IncrementingDamageBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(IncrementingDamageBehaviour.class);

    @JsonProperty
    private int damage;

    @JsonProperty
    private final int increment;

    public IncrementingDamageBehaviour(@JsonProperty("damage") int damage, @JsonProperty("increment") int increment) {
        super(true);
        this.damage = damage;
        this.increment = increment;
    }

    @Override
    public BehaviourDescription getDescription() {
        return BehaviourDescription.builder().keyword("Damage").number(damage)
                .plainString("then increase future damage by").number(increment).build();
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match, Callback callback) {
        if (cardPlayed.getCard().equals(this.getCard()) && cardPlayed.getTarget().isPresent()) {
            Damageable target = cardPlayed.getTarget().get();
            logger.info("Damaging target={} by amount={}", target, damage);
            match.getController(HealthController.class).damage(cardPlayed.getPlayer(), target, this.damage);
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
