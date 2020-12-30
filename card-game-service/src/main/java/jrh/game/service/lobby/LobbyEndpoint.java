package jrh.game.service.lobby;

import io.javalin.Javalin;
import io.javalin.http.Context;
import jrh.game.common.account.AccountId;
import jrh.game.service.account.Accounts;
import jrh.game.service.lobby.response.ActiveGamesResponse;
import jrh.game.service.lobby.response.QueueStatusResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static java.util.Collections.singleton;
import static jrh.game.service.Attributes.ACCOUNT_ID;
import static jrh.game.service.account.AccountRole.ANYONE;

public class LobbyEndpoint {

    private static final Logger logger = LogManager.getLogger(LobbyEndpoint.class);

    private final MatchManager matchManager;
    private final MatchQueue matchQueue;

    public LobbyEndpoint(Javalin javalin, Accounts accounts, MatchManager matchManager, MatchQueue matchQueue) {
        this.matchManager = matchManager;
        this.matchQueue = matchQueue;
        registerRoutes(javalin);
    }

    private void registerRoutes(Javalin javalin) {
        javalin.routes(() -> {
            path("games", () -> {
                get("count", this::activeGames, singleton(ANYONE));
                path("queue", () -> {
                    get("status", this::queueStatus);
                    post("join", this::joinQueue);
                    post("leave", this::leaveQueue);
                });
            });
        });
    }

    private void queueStatus(Context context) {
        AccountId accountId = context.attribute(ACCOUNT_ID);
        logger.debug("RX queue status request for accountId={}", accountId);
        Optional<ActiveMatch> match = matchManager.getMatchByAccountId(context.attribute(ACCOUNT_ID));
        if (match.isPresent()) {
            context.json(QueueStatusResponse.inMatch(match.get().getId()));
            return;
        }
        if (matchQueue.contains(accountId)) {
            context.json(QueueStatusResponse.queueing());
        } else {
            context.json(QueueStatusResponse.notInQueue());
        }
    }

    private void joinQueue(Context context) {
        AccountId accountId = context.attribute(ACCOUNT_ID);
        if (!matchManager.isInAMatch(accountId)) {
            logger.debug("RX join queue request for accountId={}", accountId);
            matchQueue.join(accountId);
        } else {
            logger.debug("RX join queue request for accountId={}, but they were already in a match", accountId);
        }
    }

    private void leaveQueue(Context context) {
        AccountId accountId = context.attribute(ACCOUNT_ID);
        logger.debug("RX leave queue request for accountId={}", accountId);
        matchQueue.remove(accountId);
    }

    private void activeGames(Context context) {
        context.json(new ActiveGamesResponse(matchManager.getActiveMatches()));
    }
}
