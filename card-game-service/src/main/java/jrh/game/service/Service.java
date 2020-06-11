package jrh.game.service;

import io.javalin.Javalin;

public class Service {

    private final Javalin javalin;
    private final int port;

    public Service(int port) {
        this.port = port;
        this.javalin = Javalin.create();
    }

    public void start() {
        javalin.start(port);
        Runtime.getRuntime().addShutdownHook(new Thread(javalin::stop));
    }
}
