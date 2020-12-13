package jrh.game.api.event;

import jrh.game.api.Event;
import jrh.game.api.Player;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.Instant;

public class TurnStarted implements Event {

    private final Instant startTime;
    private final Player newPlayer;

    public TurnStarted(Instant startTime, Player newPlayer) {
        this.startTime = startTime;
        this.newPlayer = newPlayer;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Player getNewPlayer() {
        return newPlayer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("startTime", startTime)
                .append("newPlayer", newPlayer).toString();
    }
}
