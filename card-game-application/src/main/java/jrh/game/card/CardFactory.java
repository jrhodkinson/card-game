package jrh.game.card;

import jrh.game.deck.Deck;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardFactory {

    private final Library library;
    private final Random random;

    public CardFactory(Library library, Random random) {
        this.library = library;
        this.random = random;
    }

    public Deck startingDeck() {
        Deck deck = new Deck();
        deck.add(library.getDebugCard(CardId.Debug.MONEY));
        deck.add(library.getDebugCard(CardId.Debug.DAMAGE));
        deck.add(library.getDebugCard(CardId.Debug.DRAW));
        Collections.shuffle(deck);
        return deck;
    }

    public Card randomCard() {
        List<Card> allCards = library.getAllCards();
        return allCards.get(random.nextInt(allCards.size()));
    }
}
