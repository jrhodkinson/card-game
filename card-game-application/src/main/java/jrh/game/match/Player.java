package jrh.game.match;

import jrh.game.User;
import jrh.game.card.Card;
import jrh.game.deck.Deck;
import jrh.game.deck.DeckAndDiscardPile;
import jrh.game.deck.Hand;
import jrh.game.util.Constants;

import java.util.Optional;

public class Player {

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

    public int getHealth() {
        return health;
    }

    void damage(int amount) {
        health -= amount;
    }

    Optional<Card> drawToHand() {
        Optional<Card> card = deckAndDiscardPile.draw();
        card.ifPresent(hand::add);
        return card;
    }
}
