package jrh.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Store {

    private final List<Card> cards = new ArrayList<>();

    public boolean contains(Card card) {
        return cards.contains(card);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public boolean removeCard(Card card) {
        return cards.remove(card);
    }
}
