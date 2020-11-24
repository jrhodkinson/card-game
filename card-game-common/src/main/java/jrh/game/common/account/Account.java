package jrh.game.common.account;

import jrh.game.common.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Account {

    private final AccountId id;
    private final String name;

    public Account(AccountId id, String name) {
        this.id = id;
        this.name = name;
    }

    public AccountId getId() {
        return id;
    }

    public String getName() {
        return name;
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
