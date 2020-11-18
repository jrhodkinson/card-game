package jrh.game.service;

import io.javalin.Javalin;
import io.javalin.core.validation.JavalinValidation;
import io.javalin.plugin.json.JavalinJackson;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.service.account.Accounts;
import jrh.game.service.account.LoginEndpoint;
import jrh.game.service.account.Sessions;
import jrh.game.service.lobby.LobbyEndpoint;
import jrh.game.service.lobby.MatchManager;
import jrh.game.service.lobby.Matchmaker;
import jrh.game.service.websocket.WebSocketConnectionManager;
import jrh.game.service.websocket.WebSocketMessageHandler;
import jrh.game.service.websocket.WebSocketPinger;

import java.util.UUID;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 7000;

    static {
        JavalinJackson.configure(ObjectMapperFactory.create());
        JavalinValidation.register(UUID.class, UUID::fromString);
    }

    private final Javalin javalin;
    private final MatchManager matchManager;
    private final Matchmaker matchmaker;

    public Server(MatchManager matchManager, Matchmaker matchmaker) {
        this.javalin = Javalin.create();
        this.matchManager = matchManager;
        this.matchmaker = matchmaker;
    }

    public void start() {
        javalin.start(PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(javalin::stop));

        new LoginEndpoint(javalin, new Accounts(), new Sessions());
        new LobbyEndpoint(javalin, matchManager, matchmaker);

        WebSocketMessageHandler webSocketMessageHandler = new WebSocketMessageHandler();
        WebSocketConnectionManager webSocketConnectionManager = new WebSocketConnectionManager(javalin, matchManager,
            webSocketMessageHandler);
        webSocketConnectionManager.start();

        new WebSocketPinger(webSocketConnectionManager, Executors.newSingleThreadScheduledExecutor());
    }
}
