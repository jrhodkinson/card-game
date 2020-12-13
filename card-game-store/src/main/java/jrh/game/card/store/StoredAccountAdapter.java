package jrh.game.card.store;

import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;
import jrh.game.common.account.AccountWithHashedPassword;

import java.time.Instant;

public class StoredAccountAdapter {

    public static AccountWithHashedPassword account(StoredAccount storedAccount) {
        Account account = new Account(AccountId.fromUUID(storedAccount.getId()), storedAccount.getName(),
                storedAccount.getEmail(), Instant.ofEpochMilli(storedAccount.getRegistered()));
        return new AccountWithHashedPassword(account, storedAccount.getBcrypt());
    }

    public static StoredAccount storedAccount(AccountWithHashedPassword account) {
        StoredAccount storedAccount = new StoredAccount();
        storedAccount.setId(account.getAccount().getId().toUUID());
        storedAccount.setName(account.getAccount().getName());
        storedAccount.setEmail(account.getAccount().getEmail());
        storedAccount.setRegistered(account.getAccount().getRegistrationTime().toEpochMilli());
        storedAccount.setBcrypt(account.getHashedPassword());
        return storedAccount;
    }
}
