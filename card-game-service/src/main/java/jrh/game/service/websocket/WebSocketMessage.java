package jrh.game.service.websocket;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonDeserialize(using = WebSocketMessageDeserializer.class)
public class WebSocketMessage<T> {

    private final WebSocketMessageType<T> type;
    private final T payload;

    WebSocketMessage(WebSocketMessageType<T> type, T payload) {
        this.type = type;
        this.payload = payload;
    }

    public WebSocketMessageType<T> getType() {
        return type;
    }

    public T getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("type", type)
            .append("payload", payload)
            .toString();
    }
}
