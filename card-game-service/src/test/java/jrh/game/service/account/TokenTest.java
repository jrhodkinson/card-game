package jrh.game.service.account;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TokenTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Token.class).verify();
    }

    @Test
    public void tokenHasAppropriateLength() {
        System.out.println(Token.randomToken().toString());
        assertThat(Token.randomToken().toString().length(), equalTo(32));
    }

    @Test
    public void parsesTokenFromString() {
        Token token = Token.randomToken();
        assertThat(Token.fromString(token.toString()), equalTo(token));
    }

}
