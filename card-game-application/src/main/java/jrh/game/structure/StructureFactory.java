package jrh.game.structure;

import jrh.game.asset.StructureLibrary;
import jrh.game.event.EventBus;
import jrh.game.structure.event.StructureCreated;

public class StructureFactory {

    private final EventBus eventBus;
    private final StructureLibrary structureLibrary;

    public StructureFactory(EventBus eventBus, StructureLibrary structureLibrary) {
        this.eventBus = eventBus;
        this.structureLibrary = structureLibrary;
    }

    public Structure create(StructureId structureId) {
        Structure structure = structureLibrary.getStructure(structureId).duplicate();
        eventBus.dispatch(new StructureCreated(structure));
        return structure;
    }
}
