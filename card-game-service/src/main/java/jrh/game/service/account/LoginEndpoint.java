package jrh.game.service.account;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import jrh.game.service.Cookies;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static java.util.Collections.singleton;
import static jrh.game.service.account.AccountRole.ANYONE;

public class LoginEndpoint {

    private static final Logger logger = LogManager.getLogger(LoginEndpoint.class);

    private final Accounts accounts;
    private final Sessions sessions;
    private final Cookies cookies;

    public LoginEndpoint(Javalin javalin, Cookies cookies, Accounts accounts, Sessions sessions) {
        this.cookies = cookies;
        this.accounts = accounts;
        this.sessions = sessions;
        registerRoutes(javalin);
    }

    private void registerRoutes(Javalin javalin) {
        javalin.routes(() -> {
            path("user", () -> {
                post("login", this::login, singleton(ANYONE));
            });
        });
    }

    private void login(Context context) {
        LoginRequest request = context.bodyAsClass(LoginRequest.class);
        logger.debug("RX loginRequest={}", request);
        Optional<AccountId> optionalAccountId = accounts.getAccountId(request.getName());
        if (optionalAccountId.isEmpty()) {
            throw new ForbiddenResponse();
        }
        AccountId accountId = optionalAccountId.get();
        Token token = sessions.getToken(accountId);
        context.cookie(cookies.accountId(accountId));
        context.cookie(cookies.token(token));
        context.json(new LoggedInResponse(accountId.toString(), accounts.getAccount(accountId).getName()));
    }

    public static class LoggedInResponse {
        private final String accountId;
        private final String name;

        public LoggedInResponse(String accountId, String name) {
            this.accountId = accountId;
            this.name = name;
        }

        public String getAccountId() {
            return accountId;
        }

        public String getName() {
            return name;
        }
    }
}
