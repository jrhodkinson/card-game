package jrh.game.structure;

import jrh.game.event.EventHandler;
import jrh.game.event.Subscribe;
import jrh.game.match.Controller;
import jrh.game.match.MutableMatch;
import jrh.game.match.api.Player;
import jrh.game.structure.event.StructureConstructed;
import jrh.game.structure.event.StructureDestroyed;
import jrh.game.structure.event.StructureTookDamage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureStateController implements Controller, EventHandler {

    private static final Logger logger = LogManager.getLogger(StructureStateController.class);

    private final MutableMatch match;
    private final StructureFactory factory;

    public StructureStateController(MutableMatch match, StructureFactory factory) {
        this.match = match;
        this.factory = factory;
    }

    @Override
    public void initialise() {
        match.getEventBus().register(this);
    }

    public Player getOwner(Structure structure) {
        if (match.getActivePlayer().getStructures().contains(structure)) {
            return match.getActivePlayer();
        } else {
            return match.getInactivePlayer();
        }
    }

    public void construct(StructureId structureId, Player player) {
        logger.info("Assigning structure={} to player={}", structureId, player);
        Structure structure = factory.create(structureId);
        match.getPlayerAsMutable(player).getStructures().add(structure);
        match.getEventBus().dispatch(new StructureConstructed(structure, player));
    }

    @Subscribe
    private void structureTookDamage(StructureTookDamage structureTookDamage) {
        Structure structure = structureTookDamage.getStructure();
        if (structure.getHealth() <= 0) {
            logger.info("Structure={} has health <= 0, destroying structure", structure);
            match.getPlayerAsMutable(getOwner(structure)).getStructures().remove(structure);
            match.getEventBus().dispatch(new StructureDestroyed(structure));
        }
    }
}
