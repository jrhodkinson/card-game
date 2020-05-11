package jrh.game.card;

import java.util.Random;

public class Store {

    private final Random random = new Random();
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
            storefront.add(index, randomCard());
        }
    }

    private void populateStorefront(int size) {
        while (storefront.size() < size) {
            storefront.add(randomCard());
        }
    }

    private Card randomCard() {
        if (random.nextBoolean()) {
            return new DamageCard(2 + random.nextInt(10));
        }
        return new MoneyCard(2 + random.nextInt(3));
    }
}
