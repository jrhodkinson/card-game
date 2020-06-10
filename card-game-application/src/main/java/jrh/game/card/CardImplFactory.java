package jrh.game.card;

import jrh.game.asset.CardImplLibrary;
import jrh.game.card.event.CardCreated;
import jrh.game.common.CardId;
import jrh.game.deck.Deck;
import jrh.game.event.EventBus;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardImplFactory {

    private final EventBus eventBus;
    private final CardImplLibrary cardImplLibrary;
    private final Random random;

    public CardImplFactory(EventBus eventBus, CardImplLibrary cardImplLibrary, Random random) {
        this.eventBus = eventBus;
        this.cardImplLibrary = cardImplLibrary;
        this.random = random;
    }

    public Deck startingDeck() {
        Deck deck = new Deck();
        deck.add(cardImplLibrary.getCard(CardId.Debug.MONEY).duplicate());
        deck.add(cardImplLibrary.getCard(CardId.Debug.DAMAGE).duplicate());
        deck.add(cardImplLibrary.getCard(CardId.Debug.DRAW).duplicate());
        Collections.shuffle(deck);
        deck.forEach(card -> eventBus.dispatch(new CardCreated(card)));
        return deck;
    }

    public CardImpl randomCard() {
        List<CardImpl> allCards = cardImplLibrary.getAllCards();
        CardImpl card = allCards.get(random.nextInt(allCards.size())).duplicate();
        eventBus.dispatch(new CardCreated(card));
        return card;
    }
}
