package jrh.game.service.account;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class TokenTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Token.class).verify();
    }

    @Test
    public void tokenHasAppropriateLength() {
        assertThat(Token.randomToken().toString().length(), greaterThan(80));
    }

    @Test
    public void parsesTokenFromString() {
        Token token = Token.randomToken();
        assertThat(Token.fromString(token.toString()), equalTo(token));
    }
}
