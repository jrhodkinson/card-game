package jrh.game.common.description;

import jrh.game.common.CardId;
import jrh.game.common.StructureId;

import java.util.Map;

public class HardCodedMapper implements StructureMapper, CardMapper {

    private final Map<StructureId, String> structureNames = Map.of(new StructureId("BLOOD_BANK"), "Blood Bank",
            new StructureId("CAMP"), "Camp", new StructureId("GARRISON"), "Garrison", new StructureId("HARBOUR"),
            "Harbour", new StructureId("MERCHANTS_GUILD"), "Merchants' Guild", new StructureId("WALL"), "Wall");

    private final Map<CardId, String> cardNames = Map.of(new CardId("REPENT"), "Repent",
        new CardId("COPPER"), "Copper", new CardId("SILVER"), "Silver", new CardId("GOLD"),
        "Gold");

    @Override
    public String getName(StructureId structureId) {
        return structureNames.getOrDefault(structureId, structureId.toString());
    }

    @Override
    public String getName(CardId cardId) {
        return cardNames.getOrDefault(cardId, cardId.toString());
    }
}
