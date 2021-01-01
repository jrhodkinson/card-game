package jrh.game.common.history;

import jrh.game.common.account.AccountId;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class HistoricMatch {

    private final UUID id;
    private final String version;
    private final Set<AccountId> players;
    private final Instant matchEndedAt;
    private final AccountId winner;

    public HistoricMatch(UUID id, String version, Set<AccountId> players, Instant matchEndedAt, AccountId winner) {
        this.id = id;
        this.version = version;
        this.players = players;
        this.matchEndedAt = matchEndedAt;
        this.winner = winner;
    }

    public UUID getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public Set<AccountId> getPlayers() {
        return players;
    }

    public Instant getMatchEndedAt() {
        return matchEndedAt;
    }

    public AccountId getWinner() {
        return winner;
    }
}
