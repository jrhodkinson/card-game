package jrh.game.api.event;

import jrh.game.api.Event;
import jrh.game.api.Player;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TurnEnded implements Event {

    private final Player previousPlayer;
    private final Player newPlayer;

    public TurnEnded(Player previousPlayer, Player newPlayer) {
        this.previousPlayer = previousPlayer;
        this.newPlayer = newPlayer;
    }

    public Player getPreviousPlayer() {
        return previousPlayer;
    }

    public Player getNewPlayer() {
        return newPlayer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("previousPlayer", previousPlayer)
            .append("newPlayer", newPlayer)
            .toString();
    }
}
