package jrh.game.match;

import jrh.game.api.Controller;
import jrh.game.api.Damageable;
import jrh.game.api.Player;
import jrh.game.api.Structure;
import jrh.game.common.EntityId;
import jrh.game.structure.MutableStructure;
import jrh.game.structure.StructureHealthController;
import jrh.game.structure.StructureStateController;
import jrh.game.structure.power.TauntPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class HealthController implements Controller {

    private static final Logger logger = LogManager.getLogger(HealthController.class);

    private final MutableMatch match;
    private final PlayerHealthController playerHealthController;
    private final StructureHealthController structureHealthController;

    HealthController(MutableMatch mutableMatch) {
        this.match = mutableMatch;
        this.playerHealthController = new PlayerHealthController(mutableMatch);
        this.structureHealthController = new StructureHealthController(mutableMatch);
    }

    public void damage(Player source, Damageable damageable, int amount) {
        if (!canDamage(source, damageable)) {
            logger.info("Player={} is currently unable to damage target={} (does something else have taunt?)", source, damageable);
            return;
        }
        int finalAmount = match.getModificationComputer().computeModifiedDamage(source, damageable, amount);
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

    private boolean canDamage(Player source, Damageable target) {
        Player otherPlayer = match.getOtherPlayer(source.getUser());
        StructureStateController structureStateController = match.getController(StructureStateController.class);
        List<EntityId> otherPlayersStructuresWithTaunt = structuresWithTaunt()
            .stream()
            .filter(s -> structureStateController.getOwner(s).equals(otherPlayer))
            .map(Structure::getEntityId)
            .collect(toList());
        if (otherPlayersStructuresWithTaunt.isEmpty()) {
            return true;
        }
        return otherPlayersStructuresWithTaunt.contains(target.getEntityId());
    }

    private List<Structure> structuresWithTaunt() {
        return match.getAllStructures()
            .stream()
            .filter(s -> s.hasPower(TauntPower.class))
            .collect(toList());
    }
}
