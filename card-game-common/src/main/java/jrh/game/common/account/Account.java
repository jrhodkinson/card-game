package jrh.game.common.account;

import jrh.game.common.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Account {

    private final AccountId id;
    private final String name;
    private final String email;

    public Account(AccountId id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public AccountId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public User toUser() {
        return new User(name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id).append("name", name)
                .toString();
    }
}
