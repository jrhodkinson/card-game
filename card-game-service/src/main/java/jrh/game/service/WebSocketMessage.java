package jrh.game.service;

public class WebSocketMessage<T> {

    private final WebSocketMessageType<T> type;
    private final T payload;

    public WebSocketMessage(WebSocketMessageType<T> type, T payload) {
        this.type = type;
        this.payload = payload;
    }

    public WebSocketMessageType<T> getType() {
        return type;
    }

    public T getPayload() {
        return payload;
    }
}
