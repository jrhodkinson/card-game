package jrh.game.service.account;

import io.javalin.Javalin;
import io.javalin.http.Context;
import jrh.game.service.Cookies;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static jrh.game.service.Status.FORBIDDEN;
import static jrh.game.service.Status.OK;

public class LoginEndpoint {

    private static final Logger logger = LogManager.getLogger(LoginEndpoint.class);

    private final Accounts accounts;
    private final Sessions sessions;

    public LoginEndpoint(Javalin javalin, Accounts accounts, Sessions sessions) {
        this.accounts = accounts;
        this.sessions = sessions;
        registerRoutes(javalin);
    }

    private void registerRoutes(Javalin javalin) {
        javalin.routes(() -> {
            path("user", () -> {
                post("login", this::login);
            });
        });
    }

    private void login(Context context) {
        logger.debug("RX login request");
        LoginRequest request = context.bodyAsClass(LoginRequest.class);
        Optional<AccountId> optionalAccountId = accounts.getAccountId(request.getName());
        if (optionalAccountId.isEmpty()) {
            context.status(FORBIDDEN);
            return;
        }
        AccountId accountId = optionalAccountId.get();
        Token token = sessions.getToken(accountId);
        context.cookie(Cookies.ACCOUNT_ID, accountId.toString());
        context.cookie(Cookies.TOKEN, token.toString());
        context.status(OK);
    }
}
