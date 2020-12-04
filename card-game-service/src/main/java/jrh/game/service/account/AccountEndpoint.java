package jrh.game.service.account;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UnauthorizedResponse;
import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;
import jrh.game.service.Cookies;
import jrh.game.service.account.request.LoginRequest;
import jrh.game.service.account.request.RegisterRequest;
import jrh.game.service.account.response.AccountResponse;
import jrh.game.service.account.response.FailedToRegisterResponse;
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

    private static final int FORBIDDEN = 403;

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
                post("logout", this::logout);
                post("register", this::register, singleton(ANYONE));
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
        logger.info("RX loginRequest for nameOrEmail={}", request.getName());
        Optional<AccountId> optionalAccountId = accounts.getAccountIdByNameOrEmail(request.getName());
        if (optionalAccountId.isEmpty()) {
            throw new ForbiddenResponse("Unrecognised username or email address");
        }
        AccountId accountId = optionalAccountId.get();
        if (accounts.isValidPassword(accountId, request.getPassword())) {
            loginAndSetCookies(context, accounts.getAccount(accountId));
        } else {
            throw new ForbiddenResponse("Invalid password");
        }
    }

    private void logout(Context context) {
        cookies.removeAccountId(context);
        cookies.removeToken(context);
    }

    private void register(Context context) {
        RegisterRequest request = context.bodyAsClass(RegisterRequest.class);
        logger.info("RX registerRequest for user={} and email={}", request.getName(), request.getEmail());
        if (validateRegisterRequest(context, request) && checkUsernameAndEmailAreFree(context, request)) {
            Account account = accounts.createAccount(request.getName(), request.getEmail(), request.getPassword());
            loginAndSetCookies(context, account);
        }
    }

    private void loginAndSetCookies(Context context, Account account) {
        Token token = sessions.getToken(account.getId());
        context.cookie(cookies.accountId(account.getId()));
        context.cookie(cookies.token(token));
        context.json(new AccountResponse(account));
    }

    private boolean validateRegisterRequest(Context context, RegisterRequest request) {
        if (!RegistrationValidation.isValidEmail(request.getEmail())) {
            forbidden(context, FailedToRegisterResponse.invalidEmailAddress());
            return false;
        }

        if (!RegistrationValidation.isValidUsername(request.getName())) {
            forbidden(context, FailedToRegisterResponse.invalidUsername());
            return false;
        }

        if (!RegistrationValidation.isValidPassword(request.getPassword())) {
            forbidden(context, FailedToRegisterResponse.invalidPassword());
            return false;
        }

        return true;
    }

    private boolean checkUsernameAndEmailAreFree(Context context, RegisterRequest request) {
        Optional<AccountId> nameAccount = accounts.getAccountIdByName(request.getName());
        if (nameAccount.isPresent()) {
            forbidden(context, FailedToRegisterResponse.usernameAlreadyExists());
            return false;
        }

        Optional<AccountId> emailAccount = accounts.getAccountIdByEmail(request.getEmail());
        if (emailAccount.isPresent()) {
            forbidden(context, FailedToRegisterResponse.emailAddressAlreadyExists());
            return false;
        }

        return true;
    }

    private void forbidden(Context context, FailedToRegisterResponse response) {
        context.json(response).status(FORBIDDEN);
    }
}
