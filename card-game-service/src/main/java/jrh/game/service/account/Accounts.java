package jrh.game.service.account;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies;
import jrh.game.card.store.AccountStore;
import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;
import jrh.game.common.account.AccountWithHashedPassword;

import java.util.Optional;

import static at.favre.lib.crypto.bcrypt.BCrypt.Version.VERSION_2A;

public class Accounts {

    private final AccountStore store;

    public Accounts(AccountStore store) {
        this.store = store;
    }

    public Optional<AccountId> getAccountIdByNameOrEmail(String nameOrEmail) {
        return getAccountIdByName(nameOrEmail).or(() -> getAccountIdByEmail(nameOrEmail));
    }

    public Optional<AccountId> getAccountIdByName(String name) {
        return store.getAccountIdByName(name);
    }

    public Optional<AccountId> getAccountIdByEmail(String email) {
        return store.getAccountIdByEmail(email);
    }

    public Account getAccount(AccountId accountId) {
        return store.getAccount(accountId).orElseThrow();
    }

    public boolean isValidPassword(AccountId accountId, String password) {
        AccountWithHashedPassword account = store.getAccountWithHashedPassword(accountId).orElseThrow();
        BCrypt.Result result = BCrypt.verifyer(VERSION_2A, LongPasswordStrategies.truncate(VERSION_2A)).verify(password.toCharArray(), account.getHashedPassword());
        return result.verified;
    }

    public Account createAccount(String name, String email, String password) {
        String hashedPassword = BCrypt.with(VERSION_2A, LongPasswordStrategies.truncate(VERSION_2A)).hashToString(12, password.toCharArray());
        Account account = new Account(AccountId.randomAccountId(), name, email);
        AccountWithHashedPassword accountWithHashedPassword = new AccountWithHashedPassword(account, hashedPassword);
        store.putAccount(accountWithHashedPassword);
        return account;
    }

}
