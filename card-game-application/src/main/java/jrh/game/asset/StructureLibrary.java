package jrh.game.asset;

import jrh.game.structure.MutableStructure;
import jrh.game.structure.StructureId;

import java.util.List;

public interface StructureLibrary {

    MutableStructure getStructure(StructureId structureId);

    List<MutableStructure> getAllStructures();
}
