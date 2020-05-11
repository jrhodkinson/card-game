package jrh.game.card;

import com.google.common.collect.ForwardingList;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class Storefront extends ForwardingList<Card> implements RandomAccess {

    private final List<Card> storefront = new ArrayList<>();

    @Override
    protected List<Card> delegate() {
        return storefront;
    }

    @Override
    public String toString() {
        return storefront.toString();
    }
}
