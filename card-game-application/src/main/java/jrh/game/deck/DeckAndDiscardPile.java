package jrh.game.deck;

import jrh.game.card.Card;

import java.util.Collections;
import java.util.Optional;

public class DeckAndDiscardPile {

    private final Deck deck = new Deck();
    private final DiscardPile discardPile = new DiscardPile();

    public Deck getDeck() {
        return deck;
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    public Optional<Card> draw() {
        if (deck.size() == 0) {
            if (discardPile.size() == 0) {
                return Optional.empty();
            }
            shuffleDiscardPileIntoDeck();
        }
        return Optional.of(deck.remove(0));
    }

    private void shuffleDiscardPileIntoDeck() {
        deck.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(deck);
    }
}
