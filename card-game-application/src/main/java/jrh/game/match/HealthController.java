package jrh.game.match;

import jrh.game.match.api.Damageable;
import jrh.game.match.api.Player;
import jrh.game.structure.MutableStructure;
import jrh.game.structure.StructureHealthController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HealthController implements Controller {

    private static final Logger logger = LogManager.getLogger(HealthController.class);

    private final HealthModificationComputer healthModificationComputer;
    private final PlayerHealthController playerHealthController;
    private final StructureHealthController structureHealthController;

    HealthController(MutableMatch mutableMatch) {
        this.healthModificationComputer = new HealthModificationComputer(mutableMatch);
        this.playerHealthController = new PlayerHealthController(mutableMatch);
        this.structureHealthController = new StructureHealthController(mutableMatch);
    }

    public void damage(Player source, Damageable damageable, int amount) {
        int finalAmount = healthModificationComputer.computeModifiedDamage(source, damageable, amount);
        if (damageable instanceof Player) {
            playerHealthController.damage((Player) damageable, finalAmount);
        } else if (damageable instanceof MutableStructure) {
            structureHealthController.damage((MutableStructure) damageable, finalAmount);
        } else {
            logger.error("Unknown damageable={} of type={}", damageable, damageable.getClass());
        }
    }

    public void heal(Player source, Damageable damageable, int amount) {
        if (damageable instanceof Player) {
            playerHealthController.heal((Player) damageable, amount);
        } else if (damageable instanceof MutableStructure) {
            structureHealthController.heal((MutableStructure) damageable, amount);
        } else {
            logger.error("Unknown damageable={} of type={}", damageable, damageable.getClass());
        }
    }
}
