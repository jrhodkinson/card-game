package jrh.game.api.event;

import jrh.game.api.Event;
import jrh.game.api.Player;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PlayerRanOutOfTime implements Event {

    private final Player player;

    public PlayerRanOutOfTime(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("player", player)
            .toString();
    }
}
