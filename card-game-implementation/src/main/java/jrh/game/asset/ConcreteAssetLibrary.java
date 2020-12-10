package jrh.game.asset;

import jrh.game.card.CardImpl;
import jrh.game.common.CardId;
import jrh.game.common.StructureId;
import jrh.game.common.asset.AssetLibrary;
import jrh.game.structure.MutableStructure;

import java.util.List;
import java.util.Optional;

public interface ConcreteAssetLibrary extends AssetLibrary {

    Optional<CardImpl> getCardImpl(CardId cardId);

    Optional<MutableStructure> getMutableStructure(StructureId structureId);

    List<CardId> getAllCardIds();

    List<StructureId> getAllStructureIds();

}
