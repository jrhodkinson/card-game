package jrh.game.service.websocket.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.common.InstanceId;

public class PlayCardDto {

    private final InstanceId card;
    private final InstanceId target;

    @JsonCreator
    public PlayCardDto(@JsonProperty("card") InstanceId card, @JsonProperty("target") InstanceId target) {
        this.card = card;
        this.target = target;
    }

    public InstanceId getCard() {
        return card;
    }

    public InstanceId getTarget() {
        return target;
    }
}
