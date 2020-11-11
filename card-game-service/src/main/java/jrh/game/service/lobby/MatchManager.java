package jrh.game.service.lobby;

import jrh.game.asset.AssetLibrary;
import jrh.game.common.User;
import jrh.game.match.MutableMatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MatchManager {

    private static final Logger logger = LogManager.getLogger(MatchManager.class);

    private final AssetLibrary assetLibrary;
    private final Map<User, UUID> matchIdByUser = new ConcurrentHashMap<>();
    private final Map<UUID, ActiveMatch> matches = new ConcurrentHashMap<>();

    public MatchManager(AssetLibrary assetLibrary) {
        this.assetLibrary = assetLibrary;
    }

    public synchronized ActiveMatch newMatch(User firstPlayer, User secondPlayer) {
        MutableMatch mutableMatch = new MutableMatch(assetLibrary, firstPlayer, secondPlayer);
        ActiveMatch match = ActiveMatch.from(mutableMatch);
        UUID matchId = match.getId();
        logger.info("Started matchId={} between {} and {}", matchId, firstPlayer, secondPlayer);
        matches.put(matchId, match);
        matchIdByUser.put(firstPlayer, matchId);
        matchIdByUser.put(secondPlayer, matchId);
        return match;
    }

    public Optional<ActiveMatch> getMatchByUser(User user) {
        return Optional.ofNullable(matchIdByUser.get(user)).map(matches::get);
    }
}
