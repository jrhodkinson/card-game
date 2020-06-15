package jrh.game.service.dto;

import jrh.game.api.Structure;
import jrh.game.common.StructureId;

import java.util.UUID;

public class StructureDto {

    private final UUID instanceId;
    private final StructureId structureId;
    private final String name;
    private final int health;

    private StructureDto(UUID instanceId, StructureId structureId, String name, int health) {
        this.instanceId = instanceId;
        this.structureId = structureId;
        this.name = name;
        this.health = health;
    }

    public static StructureDto fromStructure(Structure structure) {
        return new StructureDto(structure.getInstanceId(), structure.getStructureId(), structure.getName(),
                structure.getHealth());
    }

    public UUID getInstanceId() {
        return instanceId;
    }

    public StructureId getStructureId() {
        return structureId;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
}
