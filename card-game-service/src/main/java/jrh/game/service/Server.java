package jrh.game.service;

import io.javalin.Javalin;
import io.javalin.core.validation.JavalinValidation;
import io.javalin.plugin.json.JavalinJackson;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.service.Cookies.Environment;
import jrh.game.service.account.Accounts;
import jrh.game.service.account.AccountEndpoint;
import jrh.game.service.account.SessionAccessManager;
import jrh.game.service.account.Sessions;
import jrh.game.service.lobby.LobbyEndpoint;
import jrh.game.service.lobby.MatchManager;
import jrh.game.service.lobby.MatchQueue;
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

    private final MatchManager matchManager;
    private final MatchQueue matchQueue;
    private final Accounts accounts;

    public Server(MatchManager matchManager, MatchQueue matchQueue, Accounts accounts) {
        this.matchManager = matchManager;
        this.matchQueue = matchQueue;
        this.accounts = accounts;
    }

    public void start() {
        Cookies cookies = new Cookies(Environment.DEVELOPMENT);
        Sessions sessions = new Sessions();

        SessionAccessManager accessManager = new SessionAccessManager(cookies, sessions);

        Javalin javalin = Javalin.create(config -> config.accessManager(accessManager));
        javalin.start(PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(javalin::stop));

        new AccountEndpoint(javalin, cookies, accounts, sessions);
        new LobbyEndpoint(javalin, accounts, matchManager, matchQueue);

        WebSocketMessageHandler webSocketMessageHandler = new WebSocketMessageHandler();
        WebSocketConnectionManager webSocketConnectionManager = new WebSocketConnectionManager(javalin, accounts,
                matchManager, webSocketMessageHandler);
        webSocketConnectionManager.start();

        new WebSocketPinger(webSocketConnectionManager, Executors.newSingleThreadScheduledExecutor());
    }
}
