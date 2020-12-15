package jrh.game.card;

import com.google.common.collect.Streams;
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
import java.util.stream.Collectors;

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
        Streams.concat(
            Collections.nCopies(4, new CardId("MONEY:1")).stream(),
            Collections.nCopies(3, new CardId("DAMAGE:1")).stream(),
            Collections.nCopies(2, new CardId("MONEY:2")).stream(),
            Collections.nCopies(1, new CardId("FAVOUR")).stream()
        )
            .map(this::create)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(deck::add);
        Collections.shuffle(deck);
        return deck;
    }

    public List<CardImpl> startingStore() {
        return Collections.nCopies(2, new CardId("MONEY:2")).stream()
            .map(this::create)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
}
