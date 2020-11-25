package jrh.game.service.lobby;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import jrh.game.common.account.AccountId;
import jrh.game.service.account.Accounts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static jrh.game.service.Attributes.ACCOUNT_ID;

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
                path("queue", () -> {
                    get("status", this::queueStatus);
                    post("join", this::joinQueue);
                    post("leave", this::leaveQueue);
                });
                get("mine", this::myGames);
            });
        });
    }

    private void queueStatus(Context context) {
        AccountId accountId = context.attribute(ACCOUNT_ID);
        logger.debug("RX queue status request for accountId={}", accountId);
        if (matchQueue.contains(accountId)) {
            context.result("in queue");
        } else {
            context.result("not in queue");
        }
    }

    private void joinQueue(Context context) {
        AccountId accountId = context.attribute(ACCOUNT_ID);
        logger.debug("RX join queue request for accountId={}", accountId);
        matchQueue.join(accountId);
    }

    private void leaveQueue(Context context) {
        AccountId accountId = context.attribute(ACCOUNT_ID);
        logger.debug("RX leave queue request for accountId={}", accountId);
        matchQueue.remove(accountId);
    }

    private void myGames(Context context) {
        logger.debug("RX my games request");
        Optional<ActiveMatch> match = matchManager.getMatchByAccountId(context.attribute(ACCOUNT_ID));
        if (match.isEmpty()) {
            throw new NotFoundResponse();
        }
        context.status(200).json(match.get().getId());
    }
}
