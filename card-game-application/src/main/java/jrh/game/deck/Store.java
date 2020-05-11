package jrh.game.deck;

import jrh.game.card.Card;
import jrh.game.card.CardFactory;

import java.util.Random;

public class Store {

    private final CardFactory cardFactory = new CardFactory(new Random());
    private final Storefront storefront = new Storefront();

    public Store(int storefrontSize) {
        populateStorefront(storefrontSize);
    }

    public Storefront getStorefront() {
        return storefront;
    }

    public void removeFromStorefront(Card card) {
        int index = storefront.indexOf(card);
        if (index != -1) {
            storefront.remove(card);
            storefront.add(index, cardFactory.randomCard());
        }
    }

    private void populateStorefront(int size) {
        while (storefront.size() < size) {
            storefront.add(cardFactory.randomCard());
        }
    }
}
