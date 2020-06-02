package jrh.game.structure;

import jrh.game.event.EventHandler;
import jrh.game.event.Subscribe;
import jrh.game.match.api.Match;
import jrh.game.structure.event.StructureCreated;
import jrh.game.structure.event.StructureDestroyed;

public class StructurePowerRegistrar implements EventHandler {

    @Subscribe
    private void structureCreated(StructureCreated structureCreated, Match match) {
        structureCreated.getStructure().registerPowers(match.getEventBus());
    }

    @Subscribe
    private void structureDestroyed(StructureDestroyed structureDestroyed, Match match) {
        structureDestroyed.getStructure().unregisterPowers(match.getEventBus());
    }
}
