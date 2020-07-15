package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.asset.JsonKey;
import jrh.game.api.event.CardPlayed;
import jrh.game.common.description.AtomicDescription;
import jrh.game.api.Subscribe;
import jrh.game.match.HealthController;
import jrh.game.api.Damageable;
import jrh.game.api.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("damage")
public class DamageBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(DamageBehaviour.class);

    @JsonValue
    private final int damage;

    public DamageBehaviour(int damage) {
        super(true);
        this.damage = damage;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().keyword("Damage").number(damage).build();
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard()) && cardPlayed.getTarget().isPresent()) {
            Damageable target = cardPlayed.getTarget().get();
            logger.info("Damaging target={} by amount={}", target, damage);
            match.getController(HealthController.class).damage(cardPlayed.getPlayer(), target, this.damage);
        }
    }

    @Override
    public DamageBehaviour duplicate() {
        return new DamageBehaviour(damage);
    }
}
