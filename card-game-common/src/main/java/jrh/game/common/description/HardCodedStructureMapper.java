package jrh.game.common.description;

import jrh.game.common.StructureId;

import java.util.Map;

public class HardCodedStructureMapper implements StructureMapper {

    private final Map<StructureId, String> structureNames = Map.of(new StructureId("BLOOD_BANK"), "Blood Bank",
            new StructureId("CAMP"), "Camp", new StructureId("GARRISON"), "Garrison", new StructureId("HARBOUR"),
            "Harbour", new StructureId("MERCHANTS_GUILD"), "Merchants' Guild", new StructureId("WALL"), "Wall");

    @Override
    public String getName(StructureId structureId) {
        return structureNames.getOrDefault(structureId, structureId.toString());
    }
}
