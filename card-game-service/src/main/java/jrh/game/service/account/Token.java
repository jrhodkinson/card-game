package jrh.game.service.account;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;

public final class Token {

    private static final int TOKEN_SIZE_IN_BYTES = 64;

    private final String token;

    private Token(String token) {
        this.token = token;
    }

    public static Token randomToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[TOKEN_SIZE_IN_BYTES];
        random.nextBytes(bytes);
        Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return new Token(encoder.encodeToString(bytes));
    }

    public static Token fromString(String token) {
        return new Token(token);
    }

    @Override
    public String toString() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Token token1 = (Token) o;

        return new EqualsBuilder()
            .append(token, token1.token)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(token)
            .toHashCode();
    }
}
