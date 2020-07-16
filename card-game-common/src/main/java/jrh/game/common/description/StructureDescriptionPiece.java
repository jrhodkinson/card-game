package jrh.game.common.description;

import jrh.game.common.StructureId;

public class StructureDescriptionPiece implements DescriptionPiece {

    private final StructureId structureId;

    public StructureDescriptionPiece(StructureId structureId) {
        this.structureId = structureId;
    }

    @Override
    public String get(DescriptionContext descriptionContext) {
        return descriptionContext.getName(structureId);
    }
}
