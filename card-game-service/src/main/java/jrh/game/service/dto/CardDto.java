package jrh.game.service.dto;

import jrh.game.api.Card;
import jrh.game.common.CardId;

import java.util.UUID;

public class CardDto {

    private final UUID instanceId;
    private final CardId cardId;
    private final String name;
    private final int cost;

    private CardDto(UUID instanceId, CardId cardId, String name, int cost) {
        this.instanceId = instanceId;
        this.cardId = cardId;
        this.name = name;
        this.cost = cost;
    }

    public static CardDto fromCard(Card card) {
        return new CardDto(card.getInstanceId(), card.getCardId(), card.getName(), card.getCost());
    }

    public UUID getInstanceId() {
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
}
