package jrh.game.service;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class WebSocketMessageType<T> {

    @JsonValue
    private final String type;

    private final Class<T> payloadType;

    public WebSocketMessageType(String type, Class<T> payloadType) {
        this.type = type;
        this.payloadType = payloadType;
    }

    public Class<T> getPayloadType() {
        return payloadType;
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WebSocketMessageType that = (WebSocketMessageType) o;

        return new EqualsBuilder()
                .append(type, that.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(type)
                .toHashCode();
    }
}
