package jrh.game.service.account;

import jrh.game.common.account.AccountId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Sessions {

    private final Map<AccountId, Token> tokens = new ConcurrentHashMap<>();

    public Token getToken(AccountId accountId) {
        return tokens.computeIfAbsent(accountId, (id) -> Token.randomToken());
    }

    public boolean isValid(AccountId accountId, Token token) {
        Token expectedToken = tokens.get(accountId);
        if (token == null) {
            return false;
        }
        return token.equals(expectedToken);
    }
}
