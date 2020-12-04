package jrh.game.service.account.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class LoginRequest {

    private final String name;
    private final String password;

    public LoginRequest(@JsonProperty("name") String name, @JsonProperty("password") String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        LoginRequest that = (LoginRequest) o;

        return new EqualsBuilder().append(name, that.name).append(password, that.password).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).append(password).toHashCode();
    }
}
