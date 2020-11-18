package jrh.game.service.account;

import java.util.List;
import java.util.Optional;

public class Accounts {

    private static final List<Account> accounts = List.of(new Account(AccountId.randomAccountId(), "jack"), new Account(AccountId.randomAccountId(), "terry"));

    public Optional<AccountId> getAccountId(String name) {
        return accounts.stream()
            .filter(account -> account.getName().equals(name))
            .findAny()
            .map(Account::getId);
    }

}
