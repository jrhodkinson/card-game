package jrh.game.service.lobby;

import jrh.game.asset.ConcreteAssetLibrary;
import jrh.game.card.store.history.HistoricMatchStore;
import jrh.game.common.User;
import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;
import jrh.game.common.history.HistoricMatch;
import jrh.game.match.MutableMatch;
import jrh.game.service.account.Accounts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MatchManager {

    private static final Logger logger = LogManager.getLogger(MatchManager.class);

    private final ConcreteAssetLibrary assetLibrary;
    private final Accounts accounts;
    private final HistoricMatchStore historicMatchStore;
    private final String version;
    private final Clock clock = Clock.systemUTC();
    private final Map<AccountId, UUID> matchIdByAccountId = new ConcurrentHashMap<>();
    private final Map<UUID, ActiveMatch> activeMatches = new ConcurrentHashMap<>();
    private final Map<UUID, MatchMetadata> matchMetadata = new ConcurrentHashMap<>();

    public MatchManager(ConcreteAssetLibrary assetLibrary, Accounts accounts, HistoricMatchStore historicMatchStore,
            String version) {
        this.assetLibrary = assetLibrary;
        this.accounts = accounts;
        this.historicMatchStore = historicMatchStore;
        this.version = version;
    }

    public void startMatch(AccountId firstPlayer, AccountId secondPlayer) {
        User firstUser = accounts.getAccount(firstPlayer).toUser();
        User secondUser = accounts.getAccount(secondPlayer).toUser();
        MutableMatch mutableMatch = new MutableMatch(assetLibrary, firstUser, secondUser);
        ActiveMatch match = ActiveMatch.from(mutableMatch, firstPlayer, secondPlayer);
        UUID matchId = match.getId();
        logger.info("Started matchId={} between {} and {}", matchId, firstPlayer, secondPlayer);
        activeMatches.put(matchId, match);
        matchIdByAccountId.put(firstPlayer, matchId);
        matchIdByAccountId.put(secondPlayer, matchId);
        matchMetadata.put(matchId, new MatchMetadata(matchId, List.of(firstUser.toString(), secondUser.toString())));
        mutableMatch.start();
        mutableMatch.getEventBus().register(new MatchEndedCallback(this::matchEnded));
    }

    public boolean isInAMatch(AccountId accountId) {
        return matchIdByAccountId.containsKey(accountId);
    }

    public Optional<ActiveMatch> getMatchByAccountId(AccountId accountId) {
        return Optional.ofNullable(matchIdByAccountId.get(accountId)).map(activeMatches::get);
    }

    public ActiveMatch getMatchById(UUID id) {
        return activeMatches.get(id);
    }

    public int getActiveMatchCount() {
        return activeMatches.size();
    }

    public List<MatchMetadata> getAllActiveMatches() {
        return new ArrayList<>(matchMetadata.values());
    }

    private void matchEnded(UUID matchId, User winner) {
        ActiveMatch activeMatch = activeMatches.remove(matchId);
        matchMetadata.remove(matchId);
        if (activeMatch != null) {
            logger.info("RX MatchEnded for matchId={}", matchId);
            storeMatchHistory(activeMatch, winner);
            activeMatch.getInvolvedAccountIds().forEach(matchIdByAccountId::remove);
        } else {
            logger.warn("RX MatchEnded for matchId={} which wasn't present in the active matches map", matchId);
        }
    }

    private void storeMatchHistory(ActiveMatch activeMatch, User winner) {
        Optional<Account> optionalWinner = activeMatch
            .getInvolvedAccountIds()
            .stream()
            .map(accounts::getAccount)
            .filter(a -> a.getName().equals(winner.toString()))
            .findAny();
        if (optionalWinner.isEmpty()) {
            logger
                .error("Could not store match history for activeMatch={}, couldn't find winner's name",
                        activeMatch.getId());
            return;
        }
        HistoricMatch historicMatch = new HistoricMatch(
                activeMatch.getId(),
                version,
                activeMatch.getInvolvedAccountIds(),
                Instant.now(clock),
                optionalWinner.get().getId());
        historicMatchStore.putHistoricMatch(historicMatch);
    }
}
