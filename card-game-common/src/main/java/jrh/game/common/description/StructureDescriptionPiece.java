package jrh.game.common.description;

import jrh.game.common.StructureId;

import java.util.Optional;

public class StructureDescriptionPiece implements DescriptionPiece {

    private final StructureId structureId;

    public StructureDescriptionPiece(StructureId structureId) {
        this.structureId = structureId;
    }

    @Override
    public String get(DescriptionContext descriptionContext) {
        return descriptionContext.getName(structureId);
    }

    @Override
    public Optional<String> getHelp(DescriptionContext descriptionContext) {
        return descriptionContext.getDescription(structureId);
    }
}
