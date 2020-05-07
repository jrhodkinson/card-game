package jrh.game;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class Match {

    private final List<Player> players;
    private int currentPlayer = 0;

    public Match(List<Player> players) {
        this.players = new ArrayList<>(players);
    }

    public Player currentPlayer() {
        return players.get(currentPlayer);
    }

    public void advanceToNextPlayer() {
        currentPlayer++;
        if (currentPlayer >= players.size()) {
            currentPlayer = 0;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("players", players)
                .toString();
    }
}
