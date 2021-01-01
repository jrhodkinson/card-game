package jrh.game.card.store.history;

import jrh.game.common.account.AccountId;
import jrh.game.common.history.HistoricMatch;

import java.time.Instant;

import static java.util.stream.Collectors.toSet;

public class StoredHistoricMatchAdapter {

    public static HistoricMatch historicMatch(StoredHistoricMatch storedHistoricMatch) {
        return new HistoricMatch(
            storedHistoricMatch.getId(),
            storedHistoricMatch.getVersion(),
            storedHistoricMatch.getPlayers().stream().map(AccountId::fromUUID).collect(toSet()),
            Instant.ofEpochMilli(storedHistoricMatch.getMatchEndedAt()),
            AccountId.fromUUID(storedHistoricMatch.getWinner())
        );
    }

    public static StoredHistoricMatch storedHistoricMatch(HistoricMatch historicMatch) {
        StoredHistoricMatch storedMatch = new StoredHistoricMatch();
        storedMatch.setId(historicMatch.getId());
        storedMatch.setVersion(historicMatch.getVersion());
        storedMatch.setPlayers(historicMatch.getPlayers().stream().map(AccountId::toUUID).collect(toSet()));
        storedMatch.setMatchEndedAt(historicMatch.getMatchEndedAt().toEpochMilli());
        storedMatch.setWinner(historicMatch.getWinner().toUUID());
        return storedMatch;
    }
}
