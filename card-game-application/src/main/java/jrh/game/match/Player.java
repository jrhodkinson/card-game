package jrh.game.match;

import jrh.game.card.Card;
import jrh.game.deck.DeckAndDiscardPile;
import jrh.game.deck.Hand;
import jrh.game.util.Constants;

import java.util.Optional;

public class Player implements Target {

    private final String name;
    private final Hand hand;
    private final DeckAndDiscardPile deckAndDiscardPile = new DeckAndDiscardPile();
    private int health = Constants.INITIAL_HEALTH;

    public Player(String name, Hand hand) {
        this.name = name;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public DeckAndDiscardPile getDeckAndDiscardPile() {
        return deckAndDiscardPile;
    }

    public boolean drawToHand(int amount) {
        for (int i =0; i < amount; i++) {
            Optional<Card> card = deckAndDiscardPile.draw();
            if (card.isEmpty()) {
                return false;
            }
            hand.add(card.get());
        }
        return true;
    }

    public int getHealth() {
        return health;
    }

    public void damage(int amount) {
        health -= amount;
    }
}
