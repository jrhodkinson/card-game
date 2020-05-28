package jrh.game.match;

import jrh.game.match.api.Player;
import jrh.game.match.event.PlayerTookDamage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerHealthController {

    private static final Logger logger = LogManager.getLogger(PlayerHealthController.class);

    private final MutableMatch match;

    public PlayerHealthController(MutableMatch match) {
        this.match = match;
    }

    public void damage(Player player, int amount) {
        if (amount <= 0) {
            logger.error("Unable to damage player={} by amount={}", player, amount);
            return;
        }
        match.getPlayerAsMutable(player).changeHealth(-amount);
        match.getEventBus().dispatch(new PlayerTookDamage(player));
    }

    public void heal(Player player, int amount) {
        if (amount <= 0) {
            logger.error("Unable to heal player={} by amount={}", player, amount);
            return;
        }
        match.getPlayerAsMutable(player).changeHealth(amount);
    }
}
