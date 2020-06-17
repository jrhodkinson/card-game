package jrh.game.service.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.common.Color;

public class ColorDto {

    private final String color;

    private ColorDto(String color) {
        this.color = color;
    }

    public static ColorDto fromColor(Color color) {
        return new ColorDto(color.name());
    }

    @JsonValue
    public String getColor() {
        return color;
    }
}
