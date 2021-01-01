package jrh.game.structure.power;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.api.Damageable;
import jrh.game.api.Match;
import jrh.game.api.Player;
import jrh.game.api.Subscribe;
import jrh.game.api.event.TurnEnded;
import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Keyword;
import jrh.game.match.HealthController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("damage")
public class DamagePower extends AbstractPower {

    private static final Logger logger = LogManager.getLogger(IncrementingDamagePower.class);

    @JsonValue
    private int amount;

    public DamagePower(int amount) {
        this.amount = amount;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription
            .builder()
            .plainString("At the end of your opponent's turn,")
            .keyword(Keyword.DAMAGE)
            .plainString("them by")
            .number(amount)
            .build();
    }

    @Subscribe
    public void turnEnded(TurnEnded turnEnded, Match match) {
        if (shouldTakeEffect(turnEnded, match)) {
            Damageable target = match.getOtherPlayer(getOwner(match).getUser());
            logger.info("Damaging target={} by amount={}", target, amount);
            match.getController(HealthController.class).damage(getOwner(match), target, amount);
        }
    }

    private boolean shouldTakeEffect(TurnEnded turnEnded, Match match) {
        Player previousPlayer = turnEnded.getPreviousPlayer();
        return previousPlayer.equals(match.getOtherPlayer(getOwner(match).getUser()));
    }

    @Override
    public DamagePower duplicate() {
        return new DamagePower(amount);
    }
}
