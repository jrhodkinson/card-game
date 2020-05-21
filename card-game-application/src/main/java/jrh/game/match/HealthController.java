package jrh.game.match;

import jrh.game.event.impl.PlayerTookDamage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HealthController {

    private static final Logger logger = LogManager.getLogger(HealthController.class);

    private final Match match;

    public HealthController(Match match) {
        this.match = match;
    }

    public void damage(Player player, int amount) {
        if (amount <= 0) {
            logger.error("Unable to damage player={} by amount={}", player, amount);
            return;
        }
        player.changeHealth(-amount);
        match.getEventBus().dispatch(new PlayerTookDamage(player));
    }

    public void heal(Player player, int amount) {
        if (amount <= 0) {
            logger.error("Unable to heal player={} by amount={}", player, amount);
            return;
        }
        player.changeHealth(amount);
    }
}
