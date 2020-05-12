package jrh.game.card;

import jrh.game.deck.Deck;
import jrh.game.util.Constants;

import java.util.Collections;
import java.util.Random;

public class CardFactory {

    private final Random random;

    public CardFactory(Random random) {
        this.random = random;
    }

    public static Deck startingDeck() {
        Deck deck = new Deck();
        for (int i = 0; i < Constants.INITIAL_MONEY; i++) {
            deck.add(Library.getCard("Copper"));
        }
        for (int i = 0; i < Constants.INITIAL_DAMAGE; i++) {
            deck.add(Library.getCard("Knife"));
        }
        Collections.shuffle(deck);
        return deck;
    }

    public Card randomCard() {
        return Library.CARDS.get(random.nextInt(Library.CARDS.size()));
    }
}
