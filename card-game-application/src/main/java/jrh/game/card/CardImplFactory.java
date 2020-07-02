package jrh.game.card;

import jrh.game.Constants;
import jrh.game.asset.CardImplLibrary;
import jrh.game.api.event.CardCreated;
import jrh.game.common.CardId;
import jrh.game.deck.Deck;
import jrh.game.api.EventBus;

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
        List<CardId> startingDeck = List.of(CardId.Debug.MONEY, CardId.Debug.DAMAGE, CardId.Debug.DRAW);
        for (CardId cardId : startingDeck) {
            CardImpl cardImpl = cardImplLibrary.getCard(cardId);
            if (cardImpl != null) {
                deck.add(cardImpl.duplicate());
            }
        }
        deck.forEach(card -> eventBus.dispatch(new CardCreated(card)));
        while (deck.size() < Constants.INITIAL_HAND_SIZE) {
            deck.add(randomCard());
        }
        Collections.shuffle(deck);
        return deck;
    }

    public CardImpl randomCard() {
        List<CardImpl> allCards = cardImplLibrary.getAllCards();
        CardImpl card = allCards.get(random.nextInt(allCards.size())).duplicate();
        eventBus.dispatch(new CardCreated(card));
        return card;
    }
}
