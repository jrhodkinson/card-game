package jrh.game.deck;

import jrh.game.api.Card;
import jrh.game.api.Store;
import jrh.game.card.CardImplFactory;
import jrh.game.common.EntityId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MutableStore implements Store {

    private final CardImplFactory cardImplFactory;
    private final List<Card> row = new ArrayList<>();
    private Card next;

    public MutableStore(CardImplFactory cardImplFactory, int rowSize) {
        this.cardImplFactory = cardImplFactory;
        initialise(rowSize);
    }

    public boolean removeFromRow(Card card) {
        int index = row.indexOf(card);
        if (index != -1) {
            row.remove(card);
            row.add(index, next);
            next = cardImplFactory.randomPurchasableCard();
            return true;
        }
        return false;
    }

    private void initialise(int size) {
        row.addAll(cardImplFactory.startingStore());
        while (row.size() < size) {
            row.add(cardImplFactory.randomPurchasableCard());
        }
        Collections.shuffle(row);
        next = cardImplFactory.randomCard();
    }

    @Override
    public List<Card> getRow() {
        return Collections.unmodifiableList(row);
    }

    @Override
    public Card getNext() {
        return next;
    }

    public Optional<Card> getCard(EntityId entityId) {
        return row.stream().filter(card -> card.getEntityId().equals(entityId)).findFirst();
    }
}
