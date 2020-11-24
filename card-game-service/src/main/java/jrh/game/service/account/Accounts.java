package jrh.game.service.account;

import java.util.List;
import java.util.Optional;

public class Accounts {

    private static final List<Account> accounts = List.of(new Account(AccountId.randomAccountId(), "jack"),
            new Account(AccountId.fromString("043107f6-2c82-4306-8416-5ba9d882c323"), "terry"));

    public Optional<AccountId> getAccountId(String name) {
        return accounts.stream().filter(account -> account.getName().equals(name)).findAny().map(Account::getId);
    }

    public Account getAccount(AccountId accountId) {
        return accounts.stream().filter(account -> account.getId().equals(accountId)).findAny().orElseThrow();
    }

}
