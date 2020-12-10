package jrh.game.common.description;

import jrh.game.api.Card;
import jrh.game.api.Structure;
import jrh.game.common.CardId;
import jrh.game.common.StructureId;
import jrh.game.common.asset.AssetLibrary;

import java.util.Optional;
import java.util.stream.Collectors;

public class DescriptionContext {

    private final AssetLibrary assetLibrary;

    public DescriptionContext(AssetLibrary assetLibrary) {
        this.assetLibrary = assetLibrary;
    }

    String getName(StructureId structureId) {
        return assetLibrary.getStructure(structureId).map(Structure::getName).orElse(structureId.toString());
    }

    String getName(CardId cardId) {
        return assetLibrary.getCard(cardId).map(Card::getName).orElse(cardId.toString());
    }

    Optional<String> getDescription(StructureId structureId) {
        return assetLibrary.getStructure(structureId).map(Structure::getDescription).map(this::descriptionString);
    }

    Optional<String> getDescription(CardId cardId) {
        return assetLibrary.getCard(cardId).map(Card::getDescription).map(this::descriptionString);
    }

    private String descriptionString(Description description) {
        return description.getAtomicDescriptions().stream()
            .map(ad -> ad.get(this)).collect(Collectors.joining(". ")) + ".";
    }
}
