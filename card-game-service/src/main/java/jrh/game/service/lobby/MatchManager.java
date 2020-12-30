package jrh.game.service.lobby;

import jrh.game.asset.ConcreteAssetLibrary;
import jrh.game.common.User;
import jrh.game.common.account.AccountId;
import jrh.game.match.MutableMatch;
import jrh.game.service.account.Accounts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private final Map<AccountId, UUID> matchIdByAccountId = new ConcurrentHashMap<>();
    private final Map<UUID, ActiveMatch> activeMatches = new ConcurrentHashMap<>();
    private final Map<UUID, MatchMetadata> matchMetadata = new ConcurrentHashMap<>();

    public MatchManager(ConcreteAssetLibrary assetLibrary, Accounts accounts) {
        this.assetLibrary = assetLibrary;
        this.accounts = accounts;
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

    private void matchEnded(UUID matchId) {
        ActiveMatch activeMatch = activeMatches.remove(matchId);
        matchMetadata.remove(matchId);
        if (activeMatch != null) {
            logger.info("RX MatchEnded for matchId={}", matchId);
            activeMatch.getInvolvedAccountIds().forEach(matchIdByAccountId::remove);
        } else {
            logger.warn("RX MatchEnded for matchId={} which wasn't present in the active matches map", matchId);
        }
    }
}
