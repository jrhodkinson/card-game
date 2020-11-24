package jrh.game.card.store;

import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;

public class StoredAccountAdapter {

    public static Account account(StoredAccount account) {
        return new Account(AccountId.fromUUID(account.getId()), account.getName());
    }

    public static StoredAccount storedAccount(Account account) {
        StoredAccount storedAccount = new StoredAccount();
        storedAccount.setId(account.getId().toUUID());
        storedAccount.setName(account.getName());
        return storedAccount;
    }
}
