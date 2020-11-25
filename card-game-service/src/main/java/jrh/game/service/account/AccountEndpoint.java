package jrh.game.service.account;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import jrh.game.common.account.AccountId;
import jrh.game.service.Cookies;
import jrh.game.service.account.request.LoginRequest;
import jrh.game.service.account.response.AccountResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static java.util.Collections.singleton;
import static jrh.game.service.Attributes.ACCOUNT_ID;
import static jrh.game.service.account.AccountRole.ANYONE;

public class AccountEndpoint {

    private static final Logger logger = LogManager.getLogger(AccountEndpoint.class);

    private final Accounts accounts;
    private final Sessions sessions;
    private final Cookies cookies;

    public AccountEndpoint(Javalin javalin, Cookies cookies, Accounts accounts, Sessions sessions) {
        this.cookies = cookies;
        this.accounts = accounts;
        this.sessions = sessions;
        registerRoutes(javalin);
    }

    private void registerRoutes(Javalin javalin) {
        javalin.routes(() -> {
            path("account", () -> {
                get("me", this::getMe);
                post("login", this::login, singleton(ANYONE));
            });
        });
    }

    private void getMe(Context context) {
        AccountId accountId = context.attribute(ACCOUNT_ID);
        if (accountId == null) {
            throw new UnauthorizedResponse();
        }
        context.json(new AccountResponse(accounts.getAccount(accountId)));
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
        context.json(new AccountResponse(accounts.getAccount(accountId)));
    }

}
