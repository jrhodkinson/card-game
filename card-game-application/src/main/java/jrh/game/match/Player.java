package jrh.game.match;

import jrh.game.User;
import jrh.game.card.Card;
import jrh.game.deck.Deck;
import jrh.game.deck.DeckAndDiscardPile;
import jrh.game.deck.Hand;
import jrh.game.util.Constants;

import java.util.Optional;

public class Player implements Target {

    private final User user;
    private final Hand hand = new Hand();
    private final DeckAndDiscardPile deckAndDiscardPile;
    private int health = Constants.HEALTH;

    public Player(User user, Deck deck) {
        this.user = user;
        this.deckAndDiscardPile = new DeckAndDiscardPile(deck);
    }

    public User getUser() {
        return user;
    }

    public Hand getHand() {
        return hand;
    }

    public DeckAndDiscardPile getDeckAndDiscardPile() {
        return deckAndDiscardPile;
    }

    public void drawToHand(int amount) {
        for (int i =0; i < amount; i++) {
            Optional<Card> card = deckAndDiscardPile.draw();
            if (card.isEmpty()) {
                return;
            }
            hand.add(card.get());
        }
    }

    public int getHealth() {
        return health;
    }

    public void damage(int amount) {
        health -= amount;
    }
}
