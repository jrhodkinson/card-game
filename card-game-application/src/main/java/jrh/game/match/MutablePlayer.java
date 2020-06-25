package jrh.game.match;

import jrh.game.Constants;
import jrh.game.api.Card;
import jrh.game.api.Player;
import jrh.game.api.Structure;
import jrh.game.common.InstanceId;
import jrh.game.common.User;
import jrh.game.deck.Deck;
import jrh.game.deck.DeckAndDiscardPile;
import jrh.game.deck.Hand;
import jrh.game.structure.Structures;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MutablePlayer implements Player {

    private static final Logger logger = LogManager.getLogger(MutablePlayer.class);

    private final InstanceId instanceId = InstanceId.randomInstanceId();
    private final User user;
    private final Hand hand = new Hand();
    private final DeckAndDiscardPile deckAndDiscardPile;
    private final Structures structures = new Structures();
    private int health = Constants.HEALTH;

    public MutablePlayer(User user, Deck deck) {
        this.user = user;
        this.deckAndDiscardPile = new DeckAndDiscardPile(deck);
    }

    @Override
    public InstanceId getInstanceId() {
        return instanceId;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Hand getHand() {
        return hand;
    }

    @Override
    public List<Card> getDeck() {
        return Collections.unmodifiableList(deckAndDiscardPile.getDeck());
    }

    @Override
    public List<Card> getDiscardPile() {
        return Collections.unmodifiableList(deckAndDiscardPile.getDiscardPile());
    }

    DeckAndDiscardPile getDeckAndDiscardPile() {
        return deckAndDiscardPile;
    }

    @Override
    public Collection<Structure> getStructures() {
        return structures.unmodifiable();
    }

    public Structures getStructuresAsMutable() {
        return structures;
    }

    public int getHealth() {
        return health;
    }

    void changeHealth(int amount) {
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
