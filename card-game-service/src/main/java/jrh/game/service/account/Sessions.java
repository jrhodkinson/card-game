package jrh.game.service.account;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Sessions {

    private final Map<AccountId, Token> tokens = new ConcurrentHashMap<>();

    public Token getToken(AccountId accountId) {
        return tokens.computeIfAbsent(accountId, (id) -> Token.randomToken());
    }
}
