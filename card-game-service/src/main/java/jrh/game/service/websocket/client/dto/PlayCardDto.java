package jrh.game.service.websocket.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.common.InstanceId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("card", card)
            .append("target", target)
            .toString();
    }
}
