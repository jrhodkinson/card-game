package jrh.game.deck;

import com.google.common.collect.ForwardingList;
import jrh.game.card.Card;

import java.util.ArrayList;
import java.util.List;
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
}
