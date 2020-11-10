package jrh.game.service;

import io.javalin.Javalin;
import jrh.game.service.lobby.ActiveMatch;
import jrh.game.service.lobby.MatchManager;
import jrh.game.service.websocket.WebSocketConnectionManager;
import jrh.game.service.websocket.WebSocketMessageHandler;
import jrh.game.service.websocket.WebSocketPinger;
import jrh.game.service.websocket.server.MatchStateBroadcaster;

import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 7000;

    private final Javalin javalin;
    private final MatchManager matchManager;

    public Server(MatchManager matchManager) {
        this.javalin = Javalin.create();
        this.matchManager = matchManager;
    }

    public void start() {
        javalin.start(PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(javalin::stop));

        ActiveMatch activeMatch = matchManager.newMatch();

        WebSocketMessageHandler webSocketMessageHandler = new WebSocketMessageHandler();
        WebSocketConnectionManager webSocketConnectionManager = new WebSocketConnectionManager(javalin, matchManager,
                webSocketMessageHandler);
        webSocketConnectionManager.start();

        activeMatch.getEventBus().register(new MatchStateBroadcaster(webSocketConnectionManager));
        new WebSocketPinger(webSocketConnectionManager, Executors.newSingleThreadScheduledExecutor());
    }
}
