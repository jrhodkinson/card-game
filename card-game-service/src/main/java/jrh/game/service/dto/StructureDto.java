package jrh.game.service.dto;

import jrh.game.api.Structure;
import jrh.game.common.InstanceId;
import jrh.game.common.StructureId;

public class StructureDto {

    private final InstanceId instanceId;
    private final StructureId structureId;
    private final String name;
    private final int health;

    private StructureDto(InstanceId instanceId, StructureId structureId, String name, int health) {
        this.instanceId = instanceId;
        this.structureId = structureId;
        this.name = name;
        this.health = health;
    }

    public static StructureDto fromStructure(Structure structure) {
        return new StructureDto(structure.getInstanceId(), structure.getStructureId(), structure.getName(),
                structure.getHealth());
    }

    public InstanceId getInstanceId() {
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
