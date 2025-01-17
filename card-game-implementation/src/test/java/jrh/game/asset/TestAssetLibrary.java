package jrh.game.asset;

import jrh.game.api.Card;
import jrh.game.api.Structure;
import jrh.game.card.CardImpl;
import jrh.game.common.CardId;
import jrh.game.common.StructureId;
import jrh.game.structure.MutableStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toMap;

public class TestAssetLibrary implements ConcreteAssetLibrary {

    private final Map<CardId, CardImpl> cards;
    private final Map<StructureId, MutableStructure> structures;

    private TestAssetLibrary(List<CardImpl> cards, List<MutableStructure> structures) {
        this.cards = cards.stream().collect(toMap(Card::getCardId, c -> c));
        this.structures = structures.stream().collect(toMap(Structure::getStructureId, s -> s));
    }

    public static ConcreteAssetLibrary of(CardImpl... cards) {
        return new TestAssetLibrary(List.of(cards), emptyList());
    }

    public static ConcreteAssetLibrary of(MutableStructure... structures) {
        return new TestAssetLibrary(emptyList(), List.of(structures));
    }

    public static ConcreteAssetLibrary of(CardImpl card, MutableStructure structure) {
        return new TestAssetLibrary(List.of(card), List.of(structure));
    }

    public static ConcreteAssetLibrary of(List<CardImpl> cards, List<MutableStructure> structures) {
        return new TestAssetLibrary(new ArrayList<>(cards), new ArrayList<>(structures));
    }

    @Override
    public Optional<Card> getCard(CardId cardId) {
        return Optional.ofNullable(cards.get(cardId));
    }

    @Override
    public Optional<CardImpl> getCardImpl(CardId cardId) {
        return Optional.ofNullable(cards.get(cardId));
    }

    @Override
    public List<CardId> getAllCardIds() {
        return List.copyOf(cards.keySet());
    }

    @Override
    public Optional<Structure> getStructure(StructureId structureId) {
        return Optional.ofNullable(structures.get(structureId));
    }

    @Override
    public Optional<MutableStructure> getMutableStructure(StructureId structureId) {
        return Optional.ofNullable(structures.get(structureId));
    }

    @Override
    public List<StructureId> getAllStructureIds() {
        return List.copyOf(structures.keySet());
    }
}
