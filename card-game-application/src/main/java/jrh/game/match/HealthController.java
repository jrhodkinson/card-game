package jrh.game.match;

import jrh.game.match.api.Damageable;
import jrh.game.match.api.Player;
import jrh.game.structure.Structure;
import jrh.game.structure.StructureHealthController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HealthController implements Controller {

    private static final Logger logger = LogManager.getLogger(HealthController.class);

    private final PlayerHealthController playerHealthController;
    private final StructureHealthController structureHealthController;

    HealthController(MutableMatch mutableMatch) {
        this.playerHealthController = new PlayerHealthController(mutableMatch);
        this.structureHealthController = new StructureHealthController(mutableMatch);
    }

    public void damage(Damageable damageable, int amount) {
        if (damageable instanceof Player) {
            playerHealthController.damage((Player) damageable, amount);
        } else if (damageable instanceof Structure) {
            structureHealthController.damage((Structure) damageable, amount);
        } else {
            logger.error("Unknown damageable={} of type={}", damageable, damageable.getClass());
        }
    }

    public void heal(Damageable damageable, int amount) {
        if (damageable instanceof Player) {
            playerHealthController.heal((Player) damageable, amount);
        } else if (damageable instanceof Structure) {
            structureHealthController.heal((Structure) damageable, amount);
        } else {
            logger.error("Unknown damageable={} of type={}", damageable, damageable.getClass());
        }
    }
}
