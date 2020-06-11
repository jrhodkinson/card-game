package jrh.game.structure;

import jrh.game.common.StructureId;
import jrh.game.asset.MutableStructureLibrary;
import jrh.game.common.event.EventBus;
import jrh.game.api.event.StructureCreated;

public class StructureFactory {

    private final EventBus eventBus;
    private final MutableStructureLibrary mutableStructureLibrary;

    public StructureFactory(EventBus eventBus, MutableStructureLibrary mutableStructureLibrary) {
        this.eventBus = eventBus;
        this.mutableStructureLibrary = mutableStructureLibrary;
    }

    public MutableStructure create(StructureId structureId) {
        MutableStructure structure = mutableStructureLibrary.getStructure(structureId).duplicate();
        eventBus.dispatch(new StructureCreated(structure));
        return structure;
    }
}
