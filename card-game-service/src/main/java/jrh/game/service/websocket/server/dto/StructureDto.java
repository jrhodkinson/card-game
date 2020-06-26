package jrh.game.service.websocket.server.dto;

import jrh.game.api.Structure;
import jrh.game.common.InstanceId;
import jrh.game.common.StructureId;

public class StructureDto {

    public final InstanceId instanceId;
    public final StructureId structureId;
    public final String name;
    public final int health;

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
}
