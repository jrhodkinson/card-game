package jrh.game.service.websocket.server.dto;

import jrh.game.api.Card;
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
    public final CardDescriptionDto description;
    public final ColorDto color;
    public final boolean requiresTarget;

    private CardDto(EntityId entityId, CardId cardId, String name, String flavor, int cost, CardDescriptionDto description,
            ColorDto color, boolean requiresTarget) {
        this.entityId = entityId;
        this.cardId = cardId;
        this.name = name;
        this.flavor = flavor;
        this.cost = cost;
        this.description = description;
        this.color = color;
        this.requiresTarget = requiresTarget;
    }

    public static CardDto fromCard(Card card) {
        return new CardDto(card.getEntityId(), card.getCardId(), card.getName(), card.getFlavorText().orElse(null), card.getCost(),
                CardDescriptionDto.fromCardDescription(card.getDescription()), ColorDto.fromColor(card.getColor()),
                card.requiresTarget());
    }

    public static List<CardDto> fromCards(List<Card> cards) {
        return cards.stream().map(CardDto::fromCard).collect(toList());
    }
}
