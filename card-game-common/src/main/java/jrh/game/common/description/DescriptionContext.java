package jrh.game.common.description;

import jrh.game.common.StructureId;

public class DescriptionContext {

    private final StructureMapper structureMapper;

    public DescriptionContext(StructureMapper structureMapper) {
        this.structureMapper = structureMapper;
    }

    public static DescriptionContext defaultContext() {
        return new DescriptionContext(new HardCodedStructureMapper());
    }

    String getName(StructureId structureId) {
        return structureMapper.getName(structureId);
    }
}
