package jrh.game.service.websocket.server.dto;

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
    public final ColorDto color;
    public final boolean requiresTarget;
    public final boolean isPlayable;

    private CardDto(EntityId entityId, CardId cardId, String name, String flavor, int cost, DescriptionDto description,
            ColorDto color, boolean requiresTarget, boolean isPlayable) {
        this.entityId = entityId;
        this.cardId = cardId;
        this.name = name;
        this.flavor = flavor;
        this.cost = cost;
        this.description = description;
        this.color = color;
        this.requiresTarget = requiresTarget;
        this.isPlayable = isPlayable;
    }

    public static CardDto fromCard(Card card) {
        return new CardDto(card.getEntityId(), card.getCardId(), card.getName(), card.getFlavorText().orElse(null),
                card.getCost(), DescriptionDto.fromDescription(card.getDescription()),
                ColorDto.fromColor(card.getColor()), card.requiresTarget(),
                !card.hasBehaviour(UnplayableBehaviour.class));
    }

    public static List<CardDto> fromCards(List<Card> cards) {
        return cards.stream().map(CardDto::fromCard).collect(toList());
    }
}
