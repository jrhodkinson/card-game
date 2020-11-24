package jrh.game.common.account;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

public final class AccountId {

    private final UUID accountId;

    private AccountId(UUID id) {
        this.accountId = id;
    }

    public static AccountId randomAccountId() {
        return new AccountId(UUID.randomUUID());
    }

    public static AccountId fromUUID(UUID id) {
        return new AccountId(id);
    }

    public static AccountId fromString(String id) {
        return new AccountId(UUID.fromString(id));
    }

    public UUID toUUID() {
        return accountId;
    }

    @Override
    public String toString() {
        return accountId.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        AccountId accountId1 = (AccountId) o;

        return new EqualsBuilder().append(accountId, accountId1.accountId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(accountId).toHashCode();
    }
}
