package jrh.game.service;

import io.javalin.Javalin;
import jrh.game.api.Match;
import jrh.game.api.EventBus;
import jrh.game.service.websocket.WebSocketConnectionManager;
import jrh.game.service.websocket.WebSocketMessageHandler;
import jrh.game.service.websocket.WebSocketPinger;
import jrh.game.service.websocket.server.MatchStateBroadcaster;

import java.util.concurrent.Executors;

public class Server {

    private final Javalin javalin;
    private final int port;

    public Server(int port) {
        this.port = port;
        this.javalin = Javalin.create();
    }

    public void start(Match match, EventBus eventBus) {
        javalin.start(port);
        Runtime.getRuntime().addShutdownHook(new Thread(javalin::stop));

        WebSocketMessageHandler webSocketMessageHandler = new WebSocketMessageHandler();
        WebSocketConnectionManager webSocketConnectionManager = new WebSocketConnectionManager(javalin, match,
                webSocketMessageHandler);
        webSocketConnectionManager.start();

        eventBus.register(new MatchStateBroadcaster(webSocketConnectionManager));
        new WebSocketPinger(webSocketConnectionManager, Executors.newSingleThreadScheduledExecutor());
    }
}
