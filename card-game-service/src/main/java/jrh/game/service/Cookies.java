package jrh.game.service;

import io.javalin.http.Context;
import jrh.game.common.account.AccountId;
import jrh.game.service.account.Token;

import javax.servlet.http.Cookie;
import java.util.Optional;

public class Cookies {

    private static final String PATH = "/";
    private static final int THIRTY_DAYS = 30 * 24 * 60 * 60;

    private static final String ACCOUNT_ID = "accountId";
    private static final String TOKEN = "token";

    private final boolean useSecureCookies;

    public Cookies(Environment environment) {
        this.useSecureCookies = environment != Environment.DEVELOPMENT;
    }

    public void removeAccountId(Context context) {
        context.removeCookie(ACCOUNT_ID, PATH);
    }

    public Cookie accountId(AccountId accountId) {
        return cookie(ACCOUNT_ID, accountId.toString());
    }

    public Optional<AccountId> accountId(Context context) {
        return Optional.ofNullable(context.cookie(ACCOUNT_ID)).map(AccountId::fromString);
    }

    public void removeToken(Context context) {
        context.removeCookie(TOKEN, PATH);
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
        cookie.setMaxAge(THIRTY_DAYS);
        cookie.setPath("/");
        if (useSecureCookies) {
            cookie.setSecure(true);
        }
        return cookie;
    }

}
