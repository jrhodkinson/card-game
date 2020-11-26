package jrh.game.service.lobby;

import jrh.game.asset.AssetLibrary;
import jrh.game.common.account.AccountId;
import jrh.game.match.MutableMatch;
import jrh.game.service.account.Accounts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MatchManager {

    private static final Logger logger = LogManager.getLogger(MatchManager.class);

    private final AssetLibrary assetLibrary;
    private final Accounts accounts;
    private final Map<AccountId, UUID> matchIdByAccountId = new ConcurrentHashMap<>();
    private final Map<UUID, ActiveMatch> activeMatches = new ConcurrentHashMap<>();

    public MatchManager(AssetLibrary assetLibrary, Accounts accounts) {
        this.assetLibrary = assetLibrary;
        this.accounts = accounts;
    }

    public void startMatch(AccountId firstPlayer, AccountId secondPlayer) {
        MutableMatch mutableMatch = new MutableMatch(assetLibrary, accounts.getAccount(firstPlayer).toUser(),
                accounts.getAccount(secondPlayer).toUser());
        ActiveMatch match = ActiveMatch.from(mutableMatch, firstPlayer, secondPlayer);
        UUID matchId = match.getId();
        logger.info("Started matchId={} between {} and {}", matchId, firstPlayer, secondPlayer);
        activeMatches.put(matchId, match);
        matchIdByAccountId.put(firstPlayer, matchId);
        matchIdByAccountId.put(secondPlayer, matchId);
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

    private void matchEnded(UUID matchId) {
        ActiveMatch activeMatch = activeMatches.remove(matchId);
        if (activeMatch != null) {
            logger.info("RX MatchEnded for matchId={}", matchId);
            activeMatch.getInvolvedAccountIds().forEach(matchIdByAccountId::remove);
        } else {
            logger.warn("RX MatchEnded for matchId={} which wasn't present in the active matches map", matchId);
        }
    }
}
