package jrh.game.card;

import jrh.game.deck.Deck;
import jrh.game.util.Constants;

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
        for (int i = 0; i < Constants.INITIAL_MONEY - 1; i++) {
            deck.add(library.getCard(CardId.COPPER));
        }
        deck.add(library.getCard(CardId.VANISHING_GOLD));
        for (int i = 0; i < Constants.INITIAL_DAMAGE; i++) {
            deck.add(library.getCard(CardId.KNIFE));
        }
        Collections.shuffle(deck);
        return deck;
    }

    public Card randomCard() {
        List<Card> allCards = library.getAllCards();
        return allCards.get(random.nextInt(allCards.size()));
    }
}
