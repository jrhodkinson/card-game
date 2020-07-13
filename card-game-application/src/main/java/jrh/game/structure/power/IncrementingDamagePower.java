package jrh.game.structure.power;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.api.Damageable;
import jrh.game.api.Match;
import jrh.game.api.Player;
import jrh.game.api.Subscribe;
import jrh.game.api.event.TurnEnded;
import jrh.game.asset.JsonKey;
import jrh.game.match.HealthController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("incrementingDamage")
public class IncrementingDamagePower extends AbstractPower {

    private static final Logger logger = LogManager.getLogger(IncrementingDamagePower.class);

    @JsonProperty
    private int amount;

    @JsonProperty
    private final int increment;

    public IncrementingDamagePower(@JsonProperty("damage") int amount, @JsonProperty("increment") int increment) {
        this.amount = amount;
        this.increment = increment;
    }

    @Subscribe
    public void turnEnded(TurnEnded turnEnded, Match match) {
        if (shouldTakeEffect(turnEnded, match)) {
            Damageable target = match.getOtherPlayer(getOwner(match).getUser());
            logger.info("Damaging target={} by amount={}", target, amount);
            match.getController(HealthController.class).damage(getOwner(match), target, amount);
            logger.info("Increasing damage of {} from {} to {}", this.getStructure().getEntityId(), amount,
                    amount + increment);
            this.amount += increment;
        }
    }

    private boolean shouldTakeEffect(TurnEnded turnEnded, Match match) {
        Player previousPlayer = turnEnded.getPreviousPlayer();
        return previousPlayer.equals(match.getOtherPlayer(getOwner(match).getUser()));
    }

    @Override
    public IncrementingDamagePower duplicate() {
        return new IncrementingDamagePower(amount, increment);
    }
}
