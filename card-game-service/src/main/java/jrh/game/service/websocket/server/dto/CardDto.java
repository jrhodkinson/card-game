package jrh.game.service.websocket.server.dto;

import jrh.game.api.Card;
import jrh.game.common.CardId;
import jrh.game.common.InstanceId;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CardDto {

    public final InstanceId instanceId;
    public final CardId cardId;
    public final String name;
    public final int cost;
    public final CardDescriptionDto description;
    public final ColorDto color;
    public final boolean requiresTarget;

    private CardDto(InstanceId instanceId, CardId cardId, String name, int cost, CardDescriptionDto description,
            ColorDto color, boolean requiresTarget) {
        this.instanceId = instanceId;
        this.cardId = cardId;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.color = color;
        this.requiresTarget = requiresTarget;
    }

    public static CardDto fromCard(Card card) {
        return new CardDto(card.getInstanceId(), card.getCardId(), card.getName(), card.getCost(),
                CardDescriptionDto.fromCardDescription(card.getDescription()), ColorDto.fromColor(card.getColor()),
                card.requiresTarget());
    }

    public static List<CardDto> fromCards(List<Card> cards) {
        return cards.stream().map(CardDto::fromCard).collect(toList());
    }
}
