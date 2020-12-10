package jrh.game.structure;

import jrh.game.api.EventBus;
import jrh.game.api.event.StructureCreated;
import jrh.game.asset.ConcreteAssetLibrary;
import jrh.game.common.StructureId;

import java.util.Optional;

public class StructureFactory {

    private final EventBus eventBus;
    private final ConcreteAssetLibrary concreteAssetLibrary;

    public StructureFactory(EventBus eventBus, ConcreteAssetLibrary concreteAssetLibrary) {
        this.eventBus = eventBus;
        this.concreteAssetLibrary = concreteAssetLibrary;
    }

    public Optional<MutableStructure> create(StructureId structureId) {
        Optional<MutableStructure> optionalStructure = concreteAssetLibrary.getMutableStructure(structureId)
            .map(MutableStructure::duplicate);
        optionalStructure.ifPresent(structure -> eventBus.dispatch(new StructureCreated(structure)));
        return optionalStructure;
    }
}
