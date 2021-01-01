package jrh.game.service.lobby;

import java.util.List;
import java.util.UUID;

public class MatchMetadata {

    private final UUID matchId;
    private final List<String> players;

    public MatchMetadata(UUID matchId, List<String> players) {
        this.matchId = matchId;
        this.players = players;
    }

    public UUID getMatchId() {
        return matchId;
    }

    public List<String> getPlayers() {
        return players;
    }
}
