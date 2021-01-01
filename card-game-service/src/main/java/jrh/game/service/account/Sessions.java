package jrh.game.service.account;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jrh.game.common.account.AccountId;

import java.util.concurrent.TimeUnit;

public class Sessions {

    private final LoadingCache<AccountId, Token> tokens = CacheBuilder
        .newBuilder()
        .expireAfterAccess(3, TimeUnit.DAYS)
        .build(new CacheLoader<>() {
            @Override
            public Token load(AccountId accountId) {
                return Token.randomToken();
            }
        });

    public Token getToken(AccountId accountId) {
        return tokens.getUnchecked(accountId);
    }

    public boolean isValid(AccountId accountId, Token token) {
        Token expectedToken = tokens.getIfPresent(accountId);
        if (token == null) {
            return false;
        }

        return token.equals(expectedToken);
    }
}
