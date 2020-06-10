package jrh.game.asset;

import jrh.game.structure.MutableStructure;
import jrh.game.common.StructureId;

import java.util.List;

public interface MutableStructureLibrary {

    MutableStructure getStructure(StructureId structureId);

    List<MutableStructure> getAllStructures();
}
