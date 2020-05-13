package jrh.game.deck;

import com.google.common.collect.ForwardingList;
import jrh.game.card.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class Row extends ForwardingList<Card> implements RandomAccess {

    private final List<Card> storeRow = new ArrayList<>();

    @Override
    protected List<Card> delegate() {
        return storeRow;
    }

    @Override
    public String toString() {
        return storeRow.toString();
    }
}
