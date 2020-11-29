package jrh.game.common.description;

import jrh.game.common.CardId;
import jrh.game.common.StructureId;

public class DescriptionContext {

    private final StructureMapper structureMapper;
    private final CardMapper cardMapper;

    public DescriptionContext(StructureMapper structureMapper, CardMapper cardMapper) {
        this.structureMapper = structureMapper;
        this.cardMapper = cardMapper;
    }

    public static DescriptionContext defaultContext() {
        HardCodedMapper hardCodedMapper = new HardCodedMapper();
        return new DescriptionContext(hardCodedMapper, hardCodedMapper);
    }

    String getName(StructureId structureId) {
        return structureMapper.getName(structureId);
    }

    String getName(CardId cardId) {
        return cardMapper.getName(cardId);
    }
}
