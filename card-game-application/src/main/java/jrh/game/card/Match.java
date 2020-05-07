package jrh.game.card;

import java.util.ArrayList;
import java.util.List;

public class Match {

    private final List<Player> players;

    public Match(List<Player> players) {
        this.players = new ArrayList<>(players);
    }
}
