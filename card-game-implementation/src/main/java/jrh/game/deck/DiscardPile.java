package jrh.game.deck;

import com.google.common.collect.ForwardingList;
import jrh.game.api.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class DiscardPile extends ForwardingList<Card> implements RandomAccess {

    private final List<Card> discardPile = new ArrayList<>();

    @Override
    protected List<Card> delegate() {
        return discardPile;
    }

    @Override
    public String toString() {
        return discardPile.toString();
    }
}
