package jrh.game.service.websocket.server.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.common.Color;

public class ColorDto {

    @JsonValue
    public final String color;

    private ColorDto(String color) {
        this.color = color;
    }

    public static ColorDto fromColor(Color color) {
        return new ColorDto(color.name());
    }
}
