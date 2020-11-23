package jrh.game.service.lobby;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
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

    private final Matchmaker matchmaker;
    private final MatchManager matchManager;

    public LobbyEndpoint(Javalin javalin, Accounts accounts, MatchManager matchManager, Matchmaker matchmaker) {
        this.matchmaker = matchmaker;
        this.matchManager = matchManager;
        registerRoutes(javalin);
    }

    private void registerRoutes(Javalin javalin) {
        javalin.routes(() -> {
            path("games", () -> {
                post("queue", this::queue);
                get("mine", this::myGames);
            });
        });
    }

    private void queue(Context context) {
        logger.debug("RX queue request");
        matchmaker.queue(context.attribute(ACCOUNT_ID));
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
