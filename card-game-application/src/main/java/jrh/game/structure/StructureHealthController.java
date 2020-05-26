package jrh.game.structure;

import jrh.game.match.Controller;
import jrh.game.match.Match;
import jrh.game.structure.event.StructureTookDamage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureHealthController implements Controller {

    private static final Logger logger = LogManager.getLogger(StructureHealthController.class);

    private final Match match;

    public StructureHealthController(Match match) {
        this.match = match;
    }

    public void damage(Structure structure, int amount) {
        if (amount <= 0) {
            logger.error("Unable to damage structure={} by amount={}", structure, amount);
            return;
        }
        structure.changeHealth(-amount);
        match.getEventBus().dispatch(new StructureTookDamage(structure));
    }

    public void heal(Structure structure, int amount) {
        if (amount <= 0) {
            logger.error("Unable to heal structure={} by amount={}", structure, amount);
            return;
        }
        structure.changeHealth(amount);
    }
}
