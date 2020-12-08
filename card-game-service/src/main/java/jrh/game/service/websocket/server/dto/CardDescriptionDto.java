package jrh.game.service.websocket.server.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.common.description.Description;
import jrh.game.common.description.DescriptionContext;

import java.util.stream.Collectors;

public class CardDescriptionDto {

    @JsonValue
    public final String description;

    private CardDescriptionDto(String description) {
        this.description = description;
    }

    public static CardDescriptionDto fromCardDescription(Description description) {
        return new CardDescriptionDto(descriptionString(description));
    }

    private static String descriptionString(Description description) {
        return description.getAtomicDescriptions().stream().map(ad -> ad.get(DescriptionContext.defaultContext()))
            .collect(Collectors.joining(". ")) + ".";
    }
}
