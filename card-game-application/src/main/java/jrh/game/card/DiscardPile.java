package jrh.game.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiscardPile {

    private final List<Card> discardPile = new ArrayList<>();

    public List<Card> getCards() {
        return Collections.unmodifiableList(discardPile);
    }

    public void addCard(Card card) {
        discardPile.add(card);
    }

    public boolean removeCard(Card card) {
        return discardPile.remove(card);
    }

    @Override
    public String toString() {
        return discardPile.toString();
    }
}
