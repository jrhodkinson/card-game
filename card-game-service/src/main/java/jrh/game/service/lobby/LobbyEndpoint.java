package jrh.game.service.lobby;

import io.javalin.Javalin;
import io.javalin.http.Context;
import jrh.game.common.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.UUID;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static jrh.game.service.Status.NOT_FOUND;
import static jrh.game.service.Status.OK;

public class LobbyEndpoint {

    private static final Logger logger = LogManager.getLogger(LobbyEndpoint.class);

    private final Matchmaker matchmaker;
    private final MatchManager matchManager;

    public LobbyEndpoint(Javalin javalin, MatchManager matchManager, Matchmaker matchmaker) {
        registerRoutes(javalin);
        this.matchmaker = matchmaker;
        this.matchManager = matchManager;
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
        matchmaker.queue(new User(UUID.randomUUID().toString()));
        context.status(OK);
    }

    private void myGames(Context context) {
        logger.debug("RX my games request");
        Optional<ActiveMatch> match = matchManager.getMatchByUser(new User("Jack"));
        if (match.isPresent()) {
            context.status(200).json(match.get().getId());
        } else {
            context.status(NOT_FOUND);
        }
    }
}
