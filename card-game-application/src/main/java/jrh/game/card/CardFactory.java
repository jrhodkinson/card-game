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
            deck.add(new MoneyCard(1));
        }
        for (int i = 0; i < Constants.INITIAL_DAMAGE; i++) {
            deck.add(new DamageCard(1));
        }
        Collections.shuffle(deck);
        return deck;
    }

    public Card randomCard() {
        double rand = random.nextDouble();
        if (rand < 0.333) {
            return new DamageCard(1 + random.nextInt(5));
        } else if (rand < 0.667) {
            return new MoneyCard(1 + random.nextInt(3));
        }
        return new DrawCard(1 + random.nextInt(2));
    }
}
