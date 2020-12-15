package jrh.game.card;

import com.google.common.collect.Streams;
import jrh.game.Constants;
import jrh.game.api.Card;
import jrh.game.api.EventBus;
import jrh.game.api.event.CardCreated;
import jrh.game.asset.ConcreteAssetLibrary;
import jrh.game.common.CardId;
import jrh.game.deck.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static java.util.stream.Collectors.toList;

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
        return create(cardId).orElseThrow();
    }

    public CardImpl randomPurchasableCard() {
        List<CardId> purchasableCards = concreteAssetLibrary.getAllCardIds().stream()
                .map(concreteAssetLibrary::getCardImpl).filter(Objects::nonNull).map(Optional::get)
                .filter(Card::isPurchasable).map(Card::getCardId).collect(toList());
        CardId cardId = purchasableCards.get(random.nextInt(purchasableCards.size()));
        return create(cardId).orElseThrow();
    }

    public Deck startingDeck() {
        Deck deck = new Deck();
        List<CardId> startingDeck = new ArrayList<>();
        Streams.concat(Collections.nCopies(4, new CardId("REPENT")).stream(),
                Collections.nCopies(3, new CardId("DAMAGE:1")).stream(),
                Collections.nCopies(2, new CardId("MONEY:2")).stream(),
                Collections.nCopies(1, new CardId("FAVOUR")).stream()).map(this::create).filter(Optional::isPresent)
                .map(Optional::get).forEach(deck::add);
        Collections.shuffle(deck);
        while (deck.size() < Constants.INITIAL_HAND_SIZE) {
            deck.add(randomPurchasableCard());
        }
        return deck;
    }

    public List<CardImpl> startingStore() {
        return Collections.nCopies(2, new CardId("MONEY:2")).stream().map(this::create).filter(Optional::isPresent)
                .map(Optional::get).collect(toList());
    }
}
