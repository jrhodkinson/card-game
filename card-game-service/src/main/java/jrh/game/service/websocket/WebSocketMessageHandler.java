package jrh.game.service.websocket;

import jrh.game.api.action.PlayCard;
import jrh.game.common.InstanceId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jrh.game.service.websocket.WebSocketMessageTypes.PLAY_CARD;

public class WebSocketMessageHandler {

    private static final Logger logger = LogManager.getLogger(WebSocketMessageHandler.class);

    void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
        if (PLAY_CARD.equals(webSocketMessage.getType())) {
            WebSocketMessage<InstanceId> message = cast(webSocketMessage, PLAY_CARD.getPayloadType());
            PlayCard playCard = new PlayCard(webSocketSession.getUser(), message.getPayload(), null);
            webSocketSession.getMatch().getActionHandler().accept(playCard);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> WebSocketMessage<T> cast(WebSocketMessage<?> webSocketMessage, Class<T> clazz) {
        if (!webSocketMessage.getType().getPayloadType().equals(clazz)) {
            throw new IllegalArgumentException(String.format("Expected payload of class %s for websocket message %s", clazz, webSocketMessage));
        }
        return (WebSocketMessage<T>) webSocketMessage;
    }
}
