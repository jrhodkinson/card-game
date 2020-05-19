package jrh.game.card;

import jrh.game.deck.Deck;
import jrh.game.event.bus.EventBus;
import jrh.game.event.impl.CardCreated;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardFactory {

    private final EventBus eventBus;
    private final Library library;
    private final Random random;

    public CardFactory(EventBus eventBus, Library library, Random random) {
        this.eventBus = eventBus;
        this.library = library;
        this.random = random;
    }

    public Deck startingDeck() {
        Deck deck = new Deck();
        deck.add(library.getDebugCard(CardId.Debug.MONEY));
        deck.add(library.getDebugCard(CardId.Debug.DAMAGE));
        deck.add(library.getDebugCard(CardId.Debug.DRAW));
        Collections.shuffle(deck);
        deck.forEach(card -> eventBus.dispatch(new CardCreated(card)));
        return deck;
    }

    public Card randomCard() {
        List<Card> allCards = library.getAllCards();
        Card card = allCards.get(random.nextInt(allCards.size()));
        eventBus.dispatch(new CardCreated(card));
        return card;
    }
}
