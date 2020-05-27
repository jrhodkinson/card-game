package jrh.game.structure;

import jrh.game.asset.StructureLibrary;
import jrh.game.event.EventBus;

public class StructureFactory {

    public StructureFactory(EventBus eventBus, StructureLibrary structureLibrary) {

    }

    public Structure create(StructureId structureId) {
        return new Structure(structureId);
    }
}
