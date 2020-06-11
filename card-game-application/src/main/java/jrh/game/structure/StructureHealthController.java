package jrh.game.structure;

import jrh.game.api.Controller;
import jrh.game.match.MutableMatch;
import jrh.game.api.Structure;
import jrh.game.api.event.impl.StructureTookDamage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureHealthController implements Controller {

    private static final Logger logger = LogManager.getLogger(StructureHealthController.class);

    private final MutableMatch match;

    public StructureHealthController(MutableMatch match) {
        this.match = match;
    }

    public void damage(Structure structure, int amount) {
        if (amount <= 0) {
            logger.error("Unable to damage structure={} by amount={}", structure, amount);
            return;
        }
        logger.info("Applying damage={} to structure={}", amount, structure);
        match.getStructureAsMutable(structure).changeHealth(-amount);
        match.getEventBus().dispatch(new StructureTookDamage(structure));
    }

    public void heal(Structure structure, int amount) {
        if (amount <= 0) {
            logger.error("Unable to heal structure={} by amount={}", structure, amount);
            return;
        }
        logger.info("Applying heal={} to structure={}", amount, structure);
        match.getStructureAsMutable(structure).changeHealth(amount);
    }
}
