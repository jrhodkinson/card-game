package jrh.game.card;

import jrh.game.asset.CardLibrary;
import jrh.game.deck.Deck;
import jrh.game.event.EventBus;
import jrh.game.card.event.CardCreated;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardFactory {

    private final EventBus eventBus;
    private final CardLibrary cardLibrary;
    private final Random random;

    public CardFactory(EventBus eventBus, CardLibrary cardLibrary, Random random) {
        this.eventBus = eventBus;
        this.cardLibrary = cardLibrary;
        this.random = random;
    }

    public Deck startingDeck() {
        Deck deck = new Deck();
        deck.add(cardLibrary.getDebugCard(CardId.Debug.MONEY).duplicate());
        deck.add(cardLibrary.getDebugCard(CardId.Debug.DAMAGE).duplicate());
        deck.add(cardLibrary.getDebugCard(CardId.Debug.DRAW).duplicate());
        Collections.shuffle(deck);
        deck.forEach(card -> eventBus.dispatch(new CardCreated(card)));
        return deck;
    }

    public Card randomCard() {
        List<Card> allCards = cardLibrary.getAllCards();
        Card card = allCards.get(random.nextInt(allCards.size())).duplicate();
        eventBus.dispatch(new CardCreated(card));
        return card;
    }
}
