package jrh.game.service.lobby;

import jrh.game.asset.AssetLibrary;
import jrh.game.match.MutableMatch;
import jrh.game.common.account.AccountId;
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
    private final Map<UUID, ActiveMatch> matches = new ConcurrentHashMap<>();

    public MatchManager(AssetLibrary assetLibrary, Accounts accounts) {
        this.assetLibrary = assetLibrary;
        this.accounts = accounts;
    }

    public synchronized ActiveMatch newMatch(AccountId firstPlayer, AccountId secondPlayer) {
        MutableMatch mutableMatch = new MutableMatch(assetLibrary, accounts.getAccount(firstPlayer).toUser(),
                accounts.getAccount(secondPlayer).toUser());
        ActiveMatch match = ActiveMatch.from(mutableMatch);
        UUID matchId = match.getId();
        logger.info("Started matchId={} between {} and {}", matchId, firstPlayer, secondPlayer);
        matches.put(matchId, match);
        matchIdByAccountId.put(firstPlayer, matchId);
        matchIdByAccountId.put(secondPlayer, matchId);
        mutableMatch.start();
        return match;
    }

    public Optional<ActiveMatch> getMatchByAccountId(AccountId accountId) {
        return Optional.ofNullable(matchIdByAccountId.get(accountId)).map(matches::get);
    }

    public ActiveMatch getMatchById(UUID id) {
        return matches.get(id);
    }
}
