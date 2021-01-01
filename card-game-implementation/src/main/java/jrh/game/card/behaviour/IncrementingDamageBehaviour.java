package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.asset.JsonKey;
import jrh.game.api.event.CardPlayed;
import jrh.game.api.event.CardResolved;
import jrh.game.common.description.AtomicDescription;
import jrh.game.api.Callback;
import jrh.game.api.Subscribe;
import jrh.game.common.description.Keyword;
import jrh.game.match.HealthController;
import jrh.game.api.Damageable;
import jrh.game.api.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.DAMAGEABLE;

@JsonKey("incrementingDamage")
public class IncrementingDamageBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(IncrementingDamageBehaviour.class);

    @JsonProperty
    private int damage;

    @JsonProperty
    private final int increment;

    public IncrementingDamageBehaviour(@JsonProperty("damage") int damage, @JsonProperty("increment") int increment) {
        super(DAMAGEABLE);
        this.damage = damage;
        this.increment = increment;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription
            .builder()
            .keyword(Keyword.DAMAGE)
            .number(damage)
            .plainString("then increase future")
            .keyword(Keyword.DAMAGE)
            .plainString("by")
            .number(increment)
            .build();
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match, Callback callback) {
        if (cardPlayed.getCard().equals(this.getCard()) && cardPlayed.getDamageableTarget().isPresent()) {
            Damageable target = cardPlayed.getDamageableTarget().get();
            logger.info("Damaging target={} by amount={}", target, damage);
            match.getController(HealthController.class).damage(cardPlayed.getPlayer(), target, this.damage);
        }
    }

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match, Callback callback) {
        if (cardResolved.getCard().equals(this.getCard())) {
            logger
                .info("Increasing damage of {} from {} to {}", this.getCard().getEntityId(), damage,
                        damage + increment);
            this.damage += increment;
        }
    }

    @Override
    public IncrementingDamageBehaviour duplicate() {
        return new IncrementingDamageBehaviour(damage, increment);
    }
}
