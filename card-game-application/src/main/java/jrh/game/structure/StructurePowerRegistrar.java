package jrh.game.structure;

import jrh.game.event.EventHandler;
import jrh.game.event.Subscribe;
import jrh.game.match.MutableMatch;
import jrh.game.structure.event.StructureCreated;
import jrh.game.structure.event.StructureDestroyed;

public class StructurePowerRegistrar implements EventHandler {

    private final MutableMatch match;

    public StructurePowerRegistrar(MutableMatch match) {
        this.match = match;
    }

    @Subscribe
    private void structureCreated(StructureCreated structureCreated) {
        match.getStructureAsMutable(structureCreated.getStructure()).registerPowers(match.getEventBus());
    }

    @Subscribe
    private void structureDestroyed(StructureDestroyed structureDestroyed) {
        match.getStructureAsMutable(structureDestroyed.getStructure()).unregisterPowers(match.getEventBus());
    }
}
