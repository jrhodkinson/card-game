package jrh.game.service.dto;

import jrh.game.api.Card;
import jrh.game.card.behaviour.UnplayableBehaviour;
import jrh.game.common.CardId;
import jrh.game.common.EntityId;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CardDto {

    public final EntityId entityId;
    public final CardId cardId;
    public final String name;
    public final String flavor;
    public final int cost;
    public final DescriptionDto description;
    public final boolean requiresDamageableTarget;
    public final boolean requiresStoreTarget;
    public final boolean requiresCardInHandTarget;
    public final boolean isPlayable;

    private CardDto(EntityId entityId, CardId cardId, String name, String flavor, int cost, DescriptionDto description,
            boolean requiresDamageableTarget, boolean requiresStoreTarget, boolean requiresCardInHandTarget,
            boolean isPlayable) {
        this.entityId = entityId;
        this.cardId = cardId;
        this.name = name;
        this.flavor = flavor;
        this.cost = cost;
        this.description = description;
        this.requiresDamageableTarget = requiresDamageableTarget;
        this.requiresStoreTarget = requiresStoreTarget;
        this.requiresCardInHandTarget = requiresCardInHandTarget;
        this.isPlayable = isPlayable;
    }

    public static class Factory {

        private final DescriptionDto.Factory descriptionFactory;

        public Factory(DescriptionDto.Factory descriptionFactory) {
            this.descriptionFactory = descriptionFactory;
        }

        public CardDto cardDto(Card card) {
            return new CardDto(card.getEntityId(), card.getCardId(), card.getName(), card.getFlavorText().orElse(null),
                    card.getCost(), descriptionFactory.descriptionDto(card.getDescription()),
                    card.requiresDamageableTarget(), card.requiresStoreTarget(), card.requiresCardInHandTarget(),
                    !card.hasBehaviour(UnplayableBehaviour.class));
        }

        public List<CardDto> cardDtos(List<Card> cards) {
            return cards.stream().map(this::cardDto).collect(toList());
        }

    }
}
