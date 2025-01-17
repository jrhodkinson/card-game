package jrh.game.deck;

import com.google.common.collect.ForwardingList;
import jrh.game.api.Card;
import jrh.game.common.EntityId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.RandomAccess;

public class Hand extends ForwardingList<Card> implements RandomAccess {

    private final List<Card> cards = new ArrayList<>();

    @Override
    protected List<Card> delegate() {
        return cards;
    }

    @Override
    public String toString() {
        return cards.toString();
    }

    public Optional<Card> getCard(EntityId entityId) {
        return cards.stream().filter(card -> card.getEntityId().equals(entityId)).findFirst();
    }
}
