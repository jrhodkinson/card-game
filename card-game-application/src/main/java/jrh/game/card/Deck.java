package jrh.game.card;

import com.google.common.collect.ForwardingList;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class Deck extends ForwardingList<Card> implements RandomAccess {

    private final List<Card> deck = new ArrayList<>();

    @Override
    protected List<Card> delegate() {
        return deck;
    }

    @Override
    public String toString() {
        return deck.toString();
    }
}