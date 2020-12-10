package jrh.game.card;

import jrh.game.Constants;
import jrh.game.api.EventBus;
import jrh.game.api.event.CardCreated;
import jrh.game.asset.ConcreteAssetLibrary;
import jrh.game.common.CardId;
import jrh.game.deck.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class CardImplFactory {

    private final EventBus eventBus;
    private final ConcreteAssetLibrary concreteAssetLibrary;
    private final Random random;

    public CardImplFactory(EventBus eventBus, ConcreteAssetLibrary concreteAssetLibrary, Random random) {
        this.eventBus = eventBus;
        this.concreteAssetLibrary = concreteAssetLibrary;
        this.random = random;
    }

    public Optional<CardImpl> create(CardId cardId) {
        Optional<CardImpl> optionalCard = concreteAssetLibrary.getCardImpl(cardId).map(CardImpl::duplicate);
        optionalCard.ifPresent(card -> eventBus.dispatch(new CardCreated(card)));
        return optionalCard;
    }

    public CardImpl randomCard() {
        List<CardId> allCards = concreteAssetLibrary.getAllCardIds();
        CardId cardId = allCards.get(random.nextInt(allCards.size()));
        return create(cardId).get();
    }

    public Deck startingDeck() {
        Deck deck = new Deck();
        List<CardId> startingDeck = new ArrayList<>();
        Stream.of(CardId.Debug.MONEY, CardId.Debug.DAMAGE, CardId.Debug.DRAW).map(this::create)
                .filter(Optional::isPresent).map(Optional::get).forEach(deck::add);
        while (deck.size() < Constants.INITIAL_HAND_SIZE) {
            deck.add(randomCard());
        }
        Collections.shuffle(deck);
        return deck;
    }
}
