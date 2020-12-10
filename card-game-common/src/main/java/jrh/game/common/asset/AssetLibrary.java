package jrh.game.common.asset;

import jrh.game.api.Card;
import jrh.game.api.Structure;
import jrh.game.common.CardId;
import jrh.game.common.StructureId;

import java.util.Optional;

public interface AssetLibrary {

    Optional<Card> getCard(CardId cardId);

    Optional<Structure> getStructure(StructureId structureId);
}
