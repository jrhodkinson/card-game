package jrh.game.service.dto;

import jrh.game.api.Card;
import jrh.game.common.CardId;
import jrh.game.common.InstanceId;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CardDto {

    private final InstanceId instanceId;
    private final CardId cardId;
    private final String name;
    private final int cost;
    private final CardDescriptionDto description;
    private final ColorDto color;

    private CardDto(InstanceId instanceId, CardId cardId, String name, int cost, CardDescriptionDto description,
            ColorDto color) {
        this.instanceId = instanceId;
        this.cardId = cardId;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.color = color;
    }

    public static CardDto fromCard(Card card) {
        return new CardDto(card.getInstanceId(), card.getCardId(), card.getName(), card.getCost(),
                CardDescriptionDto.fromCardDescription(card.getDescription()), ColorDto.fromColor(card.getColor()));
    }

    public static List<CardDto> fromCards(List<Card> cards) {
        return cards.stream().map(CardDto::fromCard).collect(toList());
    }

    public InstanceId getInstanceId() {
        return instanceId;
    }

    public CardId getCardId() {
        return cardId;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public CardDescriptionDto getDescription() {
        return description;
    }

    public ColorDto getColor() {
        return color;
    }
}
