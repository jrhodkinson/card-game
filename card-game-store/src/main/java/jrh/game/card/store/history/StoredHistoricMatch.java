package jrh.game.card.store.history;

import java.util.Set;
import java.util.UUID;

public class StoredHistoricMatch {

    private UUID id;
    private String version;
    private Set<UUID> players;
    private long matchEndedAt;
    private UUID winner;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Set<UUID> getPlayers() {
        return players;
    }

    public void setPlayers(Set<UUID> players) {
        this.players = players;
    }

    public long getMatchEndedAt() {
        return matchEndedAt;
    }

    public void setMatchEndedAt(long matchEndedAt) {
        this.matchEndedAt = matchEndedAt;
    }

    public UUID getWinner() {
        return winner;
    }

    public void setWinner(UUID winner) {
        this.winner = winner;
    }
}
