package jrh.game.service.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.common.CardDescription;

public class CardDescriptionDto {

    private final String description;

    private CardDescriptionDto(String description) {
        this.description = description;
    }

    public static CardDescriptionDto fromCardDescription(CardDescription cardDescription) {
        return new CardDescriptionDto(cardDescription.toString());
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
