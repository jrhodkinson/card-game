package jrh.game.asset;

import jrh.game.card.CardImpl;
import jrh.game.common.CardId;
import jrh.game.common.StructureId;
import jrh.game.structure.MutableStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

public class TestAssetLibrary implements AssetLibrary {

    private final List<CardImpl> cards;
    private final List<MutableStructure> structures;

    private TestAssetLibrary(List<CardImpl> cards, List<MutableStructure> structures) {
        this.cards = cards;
        this.structures = structures;
    }

    public static AssetLibrary of(CardImpl... cards) {
        return new TestAssetLibrary(List.of(cards), emptyList());
    }

    public static AssetLibrary of(MutableStructure... structures) {
        return new TestAssetLibrary(emptyList(), List.of(structures));
    }

    public static AssetLibrary of(List<CardImpl> cards, List<MutableStructure> structures) {
        return new TestAssetLibrary(new ArrayList<>(cards), new ArrayList<>(structures));
    }

    @Override
    public CardImpl getCard(CardId cardId) {
        return cards.stream().filter(c -> c.getCardId().equals(cardId)).findFirst().orElse(null);
    }

    @Override
    public List<CardImpl> getAllCards() {
        return Collections.unmodifiableList(cards);
    }

    @Override
    public MutableStructure getStructure(StructureId structureId) {
        return structures.stream().filter(s -> s.getStructureId().equals(structureId)).findFirst().orElse(null);
    }

    @Override
    public List<MutableStructure> getAllStructures() {
        return Collections.unmodifiableList(structures);
    }
}
