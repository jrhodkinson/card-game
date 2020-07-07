package jrh.game.asset;

import jrh.game.common.StructureId;
import jrh.game.structure.MutableStructure;

import java.util.List;
import java.util.Optional;

public interface MutableStructureLibrary {

    Optional<MutableStructure> getStructure(StructureId structureId);

    List<StructureId> getAllStructureIds();
}
