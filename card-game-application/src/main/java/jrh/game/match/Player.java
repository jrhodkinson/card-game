package jrh.game.match;

import jrh.game.User;
import jrh.game.card.Card;
import jrh.game.deck.Deck;
import jrh.game.deck.DeckAndDiscardPile;
import jrh.game.deck.Hand;
import jrh.game.structure.Structures;
import jrh.game.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class Player {

    private static final Logger logger = LogManager.getLogger(Player.class);

    private final User user;
    private final Hand hand = new Hand();
    private final DeckAndDiscardPile deckAndDiscardPile;
    private final Structures structures = new Structures();
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

    public Structures getStructures() {
        return structures;
    }

    public int getHealth() {
        return health;
    }

    public void changeHealth(int amount) {
        health += amount;
    }

    Optional<Card> drawToHand() {
        Optional<Card> card = deckAndDiscardPile.draw();
        if (card.isPresent()) {
            hand.add(card.get());
        } else {
            logger.info("Unable to draw card, empty deck and discard pile");
        }
        return card;
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
