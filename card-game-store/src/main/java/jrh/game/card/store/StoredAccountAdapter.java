package jrh.game.card.store;

import jrh.game.common.account.Account;
import jrh.game.common.account.AccountId;
import jrh.game.common.account.AccountWithHashedPassword;
import jrh.game.common.account.Role;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StoredAccountAdapter {

    public static AccountWithHashedPassword account(StoredAccount storedAccount) {
        Account account = new Account(
            AccountId.fromUUID(storedAccount.getId()),
            storedAccount.getName(),
            storedAccount.getEmail(),
            Instant.ofEpochMilli(storedAccount.getRegistered()),
            roles(storedAccount.getRoles())
        );
        return new AccountWithHashedPassword(account, storedAccount.getBcrypt());
    }

    public static StoredAccount storedAccount(AccountWithHashedPassword account) {
        StoredAccount storedAccount = new StoredAccount();
        storedAccount.setId(account.getAccount().getId().toUUID());
        storedAccount.setName(account.getAccount().getName());
        storedAccount.setEmail(account.getAccount().getEmail());
        storedAccount.setRegistered(account.getAccount().getRegistrationTime().toEpochMilli());
        storedAccount.setRoles(storedRoles(account.getAccount().getRoles()));
        storedAccount.setBcrypt(account.getHashedPassword());
        return storedAccount;
    }

    private static Set<Role> roles(List<String> roles) {
        return roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }

    private static List<String> storedRoles(Set<Role> roles) {
        return roles.stream().map(Role::toString).collect(Collectors.toList());
    }
}
