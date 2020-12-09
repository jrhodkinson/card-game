package jrh.game.api.event;

import jrh.game.api.Event;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.Instant;

public class TurnWillEndAt implements Event {

    private final Instant instant;

    public TurnWillEndAt(Instant instant) {
        this.instant = instant;
    }

    public Instant getInstant() {
        return instant;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("instant", instant)
            .toString();
    }
}
