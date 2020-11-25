package jrh.game.service.account.response;

import jrh.game.common.account.Account;

public class AccountResponse {
    private final String accountId;
    private final String name;

    public AccountResponse(Account account) {
        this.accountId = account.getId().toString();
        this.name = account.getName();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }
}
