package jrh.game.service.websocket;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class WebSocketMessageType<T> {

    @JsonValue
    private final String type;

    private final Class<T> payloadType;

    public static <S> WebSocketMessageType<S> of(String type, Class<S> payloadType) {
        return new WebSocketMessageType<>(type, payloadType);
    }

    public static WebSocketMessageType<NoPayload> emptyPayload(String type) {
        return new WebSocketMessageType<>(type, NoPayload.class);
    }

    public static WebSocketMessageType<?> fromString(String messageType) {
        return WebSocketMessageTypes.get(messageType);
    }

    public Class<T> getPayloadType() {
        return payloadType;
    }

    private WebSocketMessageType(String type, Class<T> payloadType) {
        this.type = type;
        this.payloadType = payloadType;
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        WebSocketMessageType<?> that = (WebSocketMessageType<?>) o;

        return new EqualsBuilder().append(type, that.type).append(payloadType, that.payloadType).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(type).append(payloadType).toHashCode();
    }

}
