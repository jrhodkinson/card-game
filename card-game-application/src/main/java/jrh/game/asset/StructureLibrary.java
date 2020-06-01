package jrh.game.asset;

import jrh.game.structure.Structure;
import jrh.game.structure.StructureId;

import java.util.List;

public interface StructureLibrary {

    Structure getStructure(StructureId structureId);

    List<Structure> getAllStructures();
}
