package jrh.game.service.dto;

import jrh.game.api.Structure;
import jrh.game.common.EntityId;
import jrh.game.common.StructureId;

public class StructureDto {

    public final EntityId entityId;
    public final StructureId structureId;
    public final String name;
    public final String flavor;
    public final int health;
    public final DescriptionDto description;

    private StructureDto(EntityId entityId, StructureId structureId, String name, String flavor, int health,
            DescriptionDto description) {
        this.entityId = entityId;
        this.structureId = structureId;
        this.name = name;
        this.flavor = flavor;
        this.health = health;
        this.description = description;
    }

    public static class Factory {

        private final DescriptionDto.Factory descriptionFactory;

        public Factory(DescriptionDto.Factory descriptionFactory) {
            this.descriptionFactory = descriptionFactory;
        }

        public StructureDto structureDto(Structure structure) {
            return new StructureDto(structure.getEntityId(), structure.getStructureId(), structure.getName(),
                    structure.getFlavorText().orElse(null), structure.getHealth(),
                    descriptionFactory.descriptionDto(structure.getDescription()));
        }
    }
}
