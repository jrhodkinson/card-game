package jrh.game.service.websocket.client.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.common.EntityId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PlayCardDto {

    private final EntityId card;
    private final EntityId target;

    @JsonCreator
    public PlayCardDto(@JsonProperty("card") EntityId card, @JsonProperty("target") EntityId target) {
        this.card = card;
        this.target = target;
    }

    public EntityId getCard() {
        return card;
    }

    public EntityId getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("card", card).append("target", target)
                .toString();
    }
}
