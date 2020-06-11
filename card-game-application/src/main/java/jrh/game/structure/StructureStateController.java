package jrh.game.structure;

import jrh.game.common.StructureId;
import jrh.game.common.EventHandler;
import jrh.game.api.event.Subscribe;
import jrh.game.api.Controller;
import jrh.game.match.MutableMatch;
import jrh.game.api.Player;
import jrh.game.api.Structure;
import jrh.game.api.event.impl.StructureConstructed;
import jrh.game.api.event.impl.StructureDestroyed;
import jrh.game.api.event.impl.StructureTookDamage;
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
        if (match.getActivePlayer().getStructuresAsMutable().contains(structure.getInstanceId())) {
            return match.getActivePlayer();
        } else {
            return match.getInactivePlayer();
        }
    }

    public void construct(StructureId structureId, Player player) {
        MutableStructure structure = factory.create(structureId);
        structure.getPowers().forEach(power -> match.getEventBus().register(power));
        logger.info("Assigning structure={} to player={}", structure, player);
        match.getPlayerAsMutable(player).getStructuresAsMutable().add(structure);
        match.getEventBus().dispatch(new StructureConstructed(structure, player));
    }

    @Subscribe
    private void structureTookDamage(StructureTookDamage structureTookDamage) {
        Structure structure = structureTookDamage.getStructure();
        if (structure.getHealth() <= 0) {
            logger.info("Structure={} has health <= 0, destroying structure", structure);
            match.getStructureAsMutable(structureTookDamage.getStructure()).getPowers()
                    .forEach(power -> match.getEventBus().unregister(power));
            match.getPlayerAsMutable(getOwner(structure)).getStructuresAsMutable().remove(structure.getInstanceId());
            match.getEventBus().dispatch(new StructureDestroyed(structure));
        }
    }
}
