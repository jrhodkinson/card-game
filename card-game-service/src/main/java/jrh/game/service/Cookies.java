package jrh.game.service;

import io.javalin.http.Context;
import jrh.game.service.account.AccountId;
import jrh.game.service.account.Token;

import javax.servlet.http.Cookie;
import java.util.Optional;

public class Cookies {

    private static final String ACCOUNT_ID = "accountId";
    private static final String TOKEN = "token";

    private final boolean useSecureCookies;

    public Cookies(Environment environment) {
        this.useSecureCookies = environment != Environment.DEVELOPMENT;
    }

    public Cookie accountId(AccountId accountId) {
        return cookie(ACCOUNT_ID, accountId.toString());
    }

    public Optional<AccountId> accountId(Context context) {
        return Optional.ofNullable(context.cookie(ACCOUNT_ID)).map(AccountId::fromString);
    }

    public Cookie token(Token token) {
        return cookie(TOKEN, token.toString());
    }

    public Optional<Token> token(Context context) {
        return Optional.ofNullable(context.cookie(TOKEN)).map(Token::fromString);
    }

    private Cookie cookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        if (useSecureCookies) {
            cookie.setSecure(true);
        }
        return cookie;
    }

    public enum Environment {
        DEVELOPMENT
    }
}
