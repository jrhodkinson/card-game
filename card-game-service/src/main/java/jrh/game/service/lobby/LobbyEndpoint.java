package jrh.game.service.lobby;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.ServiceUnavailableResponse;
import jrh.game.common.account.AccountId;
import jrh.game.service.account.Accounts;
import jrh.game.service.lobby.response.MatchCountResponse;
import jrh.game.service.lobby.response.MatchListResponse;
import jrh.game.service.lobby.response.QueueStatusResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static java.util.Collections.singleton;
import static jrh.game.service.Attributes.ACCOUNT_ID;
import static jrh.game.service.account.AccountRole.ADMIN;
import static jrh.game.service.account.AccountRole.ANYONE;

public class LobbyEndpoint {

    private static final Logger logger = LogManager.getLogger(LobbyEndpoint.class);

    private static final String GAME_OFFLINE_MESSAGE = "Down for maintenance";

    private final MatchManager matchManager;
    private final MatchQueue matchQueue;
    private volatile boolean gameIsOnline = true;

    public LobbyEndpoint(Javalin javalin, Accounts accounts, MatchManager matchManager, MatchQueue matchQueue) {
        this.matchManager = matchManager;
        this.matchQueue = matchQueue;
        registerRoutes(javalin);
    }

    private void registerRoutes(Javalin javalin) {
        javalin.routes(() -> {
            path("matches", () -> {
                get("count", this::gamesCount, singleton(ANYONE));
                get("all", this::allGames);
                path("queue", () -> {
                    get("status", this::queueStatus);
                    post("join", this::joinQueue);
                    post("leave", this::leaveQueue);
                });
                get("stop", this::stop, singleton(ADMIN));
                get("start", this::start, singleton(ADMIN));
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
        if (!gameIsOnline) {
            throw new ServiceUnavailableResponse(GAME_OFFLINE_MESSAGE);
        } else if (matchQueue.contains(accountId)) {
            matchQueue.refresh(accountId);
            context.json(QueueStatusResponse.queueing());
        } else {
            context.json(QueueStatusResponse.notInQueue());
        }
    }

    private void joinQueue(Context context) {
        ensureGameIsOnline(context);
        AccountId accountId = context.attribute(ACCOUNT_ID);
        if (!matchManager.isInAMatch(accountId)) {
            logger.debug("RX join queue request for accountId={}", accountId);
            matchQueue.join(accountId);
        } else {
            logger.debug("RX join queue request for accountId={}, but they were already in a match", accountId);
        }
    }

    private void leaveQueue(Context context) {
        ensureGameIsOnline(context);
        AccountId accountId = context.attribute(ACCOUNT_ID);
        logger.debug("RX leave queue request for accountId={}", accountId);
        matchQueue.remove(accountId);
    }

    private void gamesCount(Context context) {
        context.json(new MatchCountResponse(matchManager.getActiveMatchCount()));
    }

    private void allGames(Context context) {
        context.json(new MatchListResponse(matchManager.getAllActiveMatches()));
    }

    private void ensureGameIsOnline(Context context) {
        if (!gameIsOnline) {
            throw new ServiceUnavailableResponse(GAME_OFFLINE_MESSAGE);
        }
    }

    private void stop(Context context) {
        gameIsOnline = false;
        matchQueue.clear();
    }

    private void start(Context context) {
        gameIsOnline = true;
    }
}
