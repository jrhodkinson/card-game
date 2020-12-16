package jrh.game.service;

import io.javalin.Javalin;
import io.javalin.core.validation.JavalinValidation;
import io.javalin.plugin.json.JavalinJackson;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.service.account.AccountEndpoint;
import jrh.game.service.account.Accounts;
import jrh.game.service.account.SessionAccessManager;
import jrh.game.service.account.Sessions;

import java.util.UUID;

public class NoPlayServer {

    private static final int PORT = 7000;

    static {
        JavalinJackson.configure(ObjectMapperFactory.create());
        JavalinValidation.register(UUID.class, UUID::fromString);
    }

    private final String version;
    private final Cookies cookies;
    private final Sessions sessions;
    private final Accounts accounts;

    public NoPlayServer(String version, Cookies cookies, Sessions sessions, Accounts accounts) {
        this.version = version;
        this.cookies = cookies;
        this.sessions = sessions;
        this.accounts = accounts;
    }

    public void start() {
        SessionAccessManager accessManager = new SessionAccessManager(cookies, sessions);
        Javalin javalin = Javalin.create(config -> config.accessManager(accessManager));
        javalin.start(PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(javalin::stop));

        new VersionEndpoint(javalin, version);
        new AccountEndpoint(javalin, cookies, accounts, sessions);
    }
}
