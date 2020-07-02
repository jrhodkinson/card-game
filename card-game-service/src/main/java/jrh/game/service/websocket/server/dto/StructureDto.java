package jrh.game.service.websocket.server.dto;

import jrh.game.api.Structure;
import jrh.game.common.EntityId;
import jrh.game.common.StructureId;

public class StructureDto {

    public final EntityId entityId;
    public final StructureId structureId;
    public final String name;
    public final int health;

    private StructureDto(EntityId entityId, StructureId structureId, String name, int health) {
        this.entityId = entityId;
        this.structureId = structureId;
        this.name = name;
        this.health = health;
    }

    public static StructureDto fromStructure(Structure structure) {
        return new StructureDto(structure.getEntityId(), structure.getStructureId(), structure.getName(),
                structure.getHealth());
    }
}
