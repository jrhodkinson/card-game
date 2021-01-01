package jrh.game.service;

import io.javalin.Javalin;
import io.javalin.core.validation.JavalinValidation;
import io.javalin.plugin.json.JavalinJackson;
import jrh.game.asset.ConcreteAssetLibrary;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.service.account.AccountEndpoint;
import jrh.game.service.account.Accounts;
import jrh.game.service.account.SessionAccessManager;
import jrh.game.service.account.Sessions;
import jrh.game.service.asset.AssetEndpoint;
import jrh.game.service.dto.DtoFactories;
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

    private final String version;
    private final Cookies cookies;
    private final Sessions sessions;
    private final MatchManager matchManager;
    private final MatchQueue matchQueue;
    private final Accounts accounts;
    private final ConcreteAssetLibrary assetLibrary;

    public Server(String version, Cookies cookies, Sessions sessions, MatchManager matchManager, MatchQueue matchQueue,
            Accounts accounts, ConcreteAssetLibrary assetLibrary) {
        this.version = version;
        this.cookies = cookies;
        this.sessions = sessions;
        this.matchManager = matchManager;
        this.matchQueue = matchQueue;
        this.accounts = accounts;
        this.assetLibrary = assetLibrary;
    }

    public void start() {
        SessionAccessManager accessManager = new SessionAccessManager(cookies, sessions, accounts);

        Javalin javalin = Javalin.create(config -> config.accessManager(accessManager));
        javalin.start(PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(javalin::stop));

        DtoFactories dtoFactories = new DtoFactories(assetLibrary);

        new VersionEndpoint(javalin, version);
        new AccountEndpoint(javalin, cookies, accounts, sessions);
        new LobbyEndpoint(javalin, accounts, matchManager, matchQueue);
        new AssetEndpoint(javalin, assetLibrary, dtoFactories.cardFactory(), dtoFactories.structureFactory());

        WebSocketMessageHandler webSocketMessageHandler = new WebSocketMessageHandler();
        WebSocketConnectionManager webSocketConnectionManager = new WebSocketConnectionManager(javalin, accounts,
                matchManager, webSocketMessageHandler, dtoFactories.matchFactory());
        webSocketConnectionManager.start();

        new WebSocketPinger(webSocketConnectionManager, Executors.newSingleThreadScheduledExecutor());
    }
}
