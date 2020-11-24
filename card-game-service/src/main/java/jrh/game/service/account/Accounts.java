package jrh.game.service.account;

import jrh.game.card.store.AccountStore;
import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;

import java.util.Optional;

public class Accounts {

    private final AccountStore store;

    public Accounts(AccountStore store) {
        this.store = store;
    }

    public Optional<AccountId> getAccountId(String name) {
        return store.getAccountId(name);
    }

    public Account getAccount(AccountId accountId) {
        return store.getAccount(accountId).orElseThrow();
    }

}
