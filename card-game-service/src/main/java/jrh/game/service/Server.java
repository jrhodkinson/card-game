package jrh.game.service;

import io.javalin.Javalin;
import io.javalin.core.validation.JavalinValidation;
import io.javalin.plugin.json.JavalinJackson;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.common.asset.AssetLibrary;
import jrh.game.common.description.DescriptionContext;
import jrh.game.service.Cookies.Environment;
import jrh.game.service.account.AccountEndpoint;
import jrh.game.service.account.Accounts;
import jrh.game.service.account.SessionAccessManager;
import jrh.game.service.account.Sessions;
import jrh.game.service.lobby.LobbyEndpoint;
import jrh.game.service.lobby.MatchManager;
import jrh.game.service.lobby.MatchQueue;
import jrh.game.service.websocket.WebSocketConnectionManager;
import jrh.game.service.websocket.WebSocketMessageHandler;
import jrh.game.service.websocket.WebSocketPinger;
import jrh.game.service.websocket.server.dto.CardDto;
import jrh.game.service.websocket.server.dto.DescriptionDto;
import jrh.game.service.websocket.server.dto.MatchDto;
import jrh.game.service.websocket.server.dto.PlayerDto;
import jrh.game.service.websocket.server.dto.StoreDto;
import jrh.game.service.websocket.server.dto.StructureDto;
import jrh.game.service.websocket.server.dto.TurnDto;

import java.util.UUID;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 7000;

    static {
        JavalinJackson.configure(ObjectMapperFactory.create());
        JavalinValidation.register(UUID.class, UUID::fromString);
    }

    private final String version;
    private final MatchManager matchManager;
    private final MatchQueue matchQueue;
    private final Accounts accounts;
    private final AssetLibrary assetLibrary;

    public Server(String version, MatchManager matchManager, MatchQueue matchQueue, Accounts accounts,
            AssetLibrary assetLibrary) {
        this.version = version;
        this.matchManager = matchManager;
        this.matchQueue = matchQueue;
        this.accounts = accounts;
        this.assetLibrary = assetLibrary;
    }

    public void start() {
        Cookies cookies = new Cookies(Environment.DEVELOPMENT);
        Sessions sessions = new Sessions();

        SessionAccessManager accessManager = new SessionAccessManager(cookies, sessions);

        Javalin javalin = Javalin.create(config -> config.accessManager(accessManager));
        javalin.start(PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(javalin::stop));

        new VersionEndpoint(javalin, version);
        new AccountEndpoint(javalin, cookies, accounts, sessions);
        new LobbyEndpoint(javalin, accounts, matchManager, matchQueue);

        WebSocketMessageHandler webSocketMessageHandler = new WebSocketMessageHandler();
        MatchDto.Factory matchDtoFactory = matchDtoFactory();
        WebSocketConnectionManager webSocketConnectionManager = new WebSocketConnectionManager(javalin, accounts,
                matchManager, webSocketMessageHandler, matchDtoFactory);
        webSocketConnectionManager.start();

        new WebSocketPinger(webSocketConnectionManager, Executors.newSingleThreadScheduledExecutor());
    }

    private MatchDto.Factory matchDtoFactory() {
        DescriptionContext descriptionContext = new DescriptionContext(assetLibrary);
        DescriptionDto.Factory descriptionFactory = new DescriptionDto.Factory(descriptionContext);
        CardDto.Factory cardFactory = new CardDto.Factory(descriptionFactory);
        StructureDto.Factory structureFactory = new StructureDto.Factory(descriptionFactory);
        PlayerDto.Factory playerFactory = new PlayerDto.Factory(cardFactory, structureFactory);
        TurnDto.Factory turnFactory = new TurnDto.Factory(cardFactory);
        StoreDto.Factory storeFactory = new StoreDto.Factory(cardFactory);
        return new MatchDto.Factory(playerFactory, turnFactory, storeFactory);
    }
}
