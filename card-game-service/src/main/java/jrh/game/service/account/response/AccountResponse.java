package jrh.game.service.account.response;

import jrh.game.common.account.Account;
import jrh.game.common.account.Role;

import java.util.Set;
import java.util.stream.Collectors;

public class AccountResponse {
    private final String accountId;
    private final String name;
    private final Set<String> roles;

    public AccountResponse(Account account) {
        this.accountId = account.getId().toString();
        this.name = account.getName();
        this.roles = account.getRoles().stream().map(Role::toString).collect(Collectors.toSet());
    }

    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
