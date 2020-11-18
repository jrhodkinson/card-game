package jrh.game.service.account;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AccountIdTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(AccountId.class).verify();
    }

    @Test
    public void roundTrips() {
        AccountId accountId = AccountId.randomAccountId();
        assertThat(AccountId.fromString(accountId.toString()), equalTo(accountId));
    }

    @Test
    public void randomAccountIdIsAUUID() {
        AccountId accountId = AccountId.randomAccountId();
        assertThat(accountId.toString().length(), equalTo(UUID.randomUUID().toString().length()));
    }

    @Test
    public void parsesAccountId() {
        UUID accountId = UUID.randomUUID();
        assertThat(AccountId.fromUUID(accountId).toString(), equalTo(accountId.toString()));
        assertThat(AccountId.fromString(accountId.toString()).toString(), equalTo(accountId.toString()));
    }
}
