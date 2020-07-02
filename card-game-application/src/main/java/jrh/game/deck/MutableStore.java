package jrh.game.deck;

import jrh.game.api.Card;
import jrh.game.api.Store;
import jrh.game.card.CardImplFactory;
import jrh.game.common.EntityId;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MutableStore implements Store {

    private final CardImplFactory cardImplFactory;
    private final Row row;

    public MutableStore(CardImplFactory cardImplFactory, int rowSize) {
        this.cardImplFactory = cardImplFactory;
        this.row = new Row();
        populateRow(rowSize);
    }

    public boolean removeFromRow(Card card) {
        int index = row.indexOf(card);
        if (index != -1) {
            row.remove(card);
            row.add(index, cardImplFactory.randomCard());
            return true;
        }
        return false;
    }

    private void populateRow(int size) {
        while (row.size() < size) {
            row.add(cardImplFactory.randomCard());
        }
    }

    @Override
    public List<Card> getRow() {
        return Collections.unmodifiableList(row);
    }

    public Optional<Card> getCard(EntityId entityId) {
        return row.stream().filter(card -> card.getEntityId().equals(entityId)).findFirst();
    }
}
