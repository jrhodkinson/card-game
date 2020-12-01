package jrh.game.card.store;

import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;
import jrh.game.common.account.AccountWithHashedPassword;

public class StoredAccountAdapter {

    public static AccountWithHashedPassword account(StoredAccount storedAccount) {
        Account account = new Account(AccountId.fromUUID(storedAccount.getId()), storedAccount.getName(), storedAccount.getEmail());
        return new AccountWithHashedPassword(account, storedAccount.getBcrypt());
    }

    public static StoredAccount storedAccount(AccountWithHashedPassword account) {
        StoredAccount storedAccount = new StoredAccount();
        storedAccount.setId(account.getAccount().getId().toUUID());
        storedAccount.setName(account.getAccount().getName());
        storedAccount.setEmail(account.getAccount().getEmail());
        storedAccount.setBcrypt(account.getHashedPassword());
        return storedAccount;
    }
}
