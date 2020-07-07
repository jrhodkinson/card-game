package jrh.game.structure;

import jrh.game.api.Controller;
import jrh.game.api.Player;
import jrh.game.api.Structure;
import jrh.game.api.Subscribe;
import jrh.game.api.event.StructureConstructed;
import jrh.game.api.event.StructureDestroyed;
import jrh.game.api.event.StructureTookDamage;
import jrh.game.common.EventHandler;
import jrh.game.common.StructureId;
import jrh.game.match.MutableMatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

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
        if (match.getActivePlayer().getStructuresAsMutable().contains(structure.getEntityId())) {
            return match.getActivePlayer();
        } else {
            return match.getInactivePlayer();
        }
    }

    public void construct(StructureId structureId, Player player) {
        Optional<MutableStructure> optionalStructure = factory.create(structureId);
        if (optionalStructure.isEmpty()) {
            logger.error(
                    "Could not construct structure with structureId={} for player={}. It wasn't in the structure factory",
                    structureId, player);
            return;
        }
        MutableStructure structure = optionalStructure.get();
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
            match.getPlayerAsMutable(getOwner(structure)).getStructuresAsMutable().remove(structure.getEntityId());
            match.getEventBus().dispatch(new StructureDestroyed(structure));
        }
    }
}
