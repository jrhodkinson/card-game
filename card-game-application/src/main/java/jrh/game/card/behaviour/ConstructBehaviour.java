package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.structure.StructureId;

public class ConstructBehaviour {

    @JsonValue
    private final StructureId structureId;

    public ConstructBehaviour(StructureId structureId) {
        this.structureId = structureId;
    }
}
