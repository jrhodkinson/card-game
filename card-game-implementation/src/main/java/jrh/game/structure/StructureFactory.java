package jrh.game.structure;

import jrh.game.api.EventBus;
import jrh.game.api.event.StructureCreated;
import jrh.game.asset.MutableStructureLibrary;
import jrh.game.common.StructureId;

import java.util.Optional;

public class StructureFactory {

    private final EventBus eventBus;
    private final MutableStructureLibrary mutableStructureLibrary;

    public StructureFactory(EventBus eventBus, MutableStructureLibrary mutableStructureLibrary) {
        this.eventBus = eventBus;
        this.mutableStructureLibrary = mutableStructureLibrary;
    }

    public Optional<MutableStructure> create(StructureId structureId) {
        Optional<MutableStructure> optionalStructure = mutableStructureLibrary.getStructure(structureId)
                .map(MutableStructure::duplicate);
        optionalStructure.ifPresent(structure -> eventBus.dispatch(new StructureCreated(structure)));
        return optionalStructure;
    }
}
