package jrh.game.service;

import io.javalin.Javalin;
import jrh.game.api.event.EventBus;
import jrh.game.service.websocket.WebSocketConnectionManager;
import jrh.game.service.websocket.WebSocketPinger;

import java.util.concurrent.Executors;

public class Service {

    private final Javalin javalin;
    private final int port;

    public Service(int port) {
        this.port = port;
        this.javalin = Javalin.create();
    }

    public void start(EventBus eventBus) {
        javalin.start(port);
        Runtime.getRuntime().addShutdownHook(new Thread(javalin::stop));

        WebSocketConnectionManager webSocketConnectionManager = new WebSocketConnectionManager(javalin, "match");
        webSocketConnectionManager.start();

        eventBus.register(new MatchStateBroadcaster(webSocketConnectionManager));
        new WebSocketPinger(webSocketConnectionManager, Executors.newSingleThreadScheduledExecutor());
    }
}
