package jrh.game.service.websocket;

import jrh.game.api.action.BuyCard;
import jrh.game.api.action.EndTurn;
import jrh.game.api.action.PlayCard;
import jrh.game.common.EntityId;
import jrh.game.service.websocket.client.dto.PlayCardDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static jrh.game.service.websocket.client.ClientWebSocketMessageTypes.BUY_CARD;
import static jrh.game.service.websocket.client.ClientWebSocketMessageTypes.PLAY_CARD;

public class WebSocketMessageHandler {

    private static final Logger logger = LogManager.getLogger(WebSocketMessageHandler.class);

    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
        with(webSocketMessage, webSocketSession)
            .handle(this::endTurn)
            .handle(PLAY_CARD, this::playCard)
            .handle(BUY_CARD, this::buyCard)
            .end();
    }

    private void endTurn(WebSocketSession webSocketSession) {
        if (webSocketSession.getUser() != null) {
            EndTurn endTurn = new EndTurn(webSocketSession.getUser());
            logger.info("TX endTurn={}", endTurn);
            webSocketSession.getMatch().getActionHandler().accept(endTurn);
        }
    }

    private void playCard(WebSocketSession webSocketSession, PlayCardDto playCardDto) {
        PlayCard playCard = new PlayCard(webSocketSession.getUser(), playCardDto.getCard(), playCardDto.getTarget());
        logger.info("TX playCard={}", playCard);
        webSocketSession.getMatch().getActionHandler().accept(playCard);
    }

    private void buyCard(WebSocketSession webSocketSession, EntityId entityId) {
        BuyCard buyCard = new BuyCard(webSocketSession.getUser(), entityId);
        logger.info("TX buyCard={}", buyCard);
        webSocketSession.getMatch().getActionHandler().accept(buyCard);
    }

    private static MessageContext with(WebSocketMessage<?> webSocketMessage, WebSocketSession webSocketSession) {
        return new MessageContext(webSocketMessage, webSocketSession);
    }

    private static class MessageContext {

        private final WebSocketMessage<?> webSocketMessage;
        private final WebSocketSession webSocketSession;
        private boolean wasHandled = false;

        private MessageContext(WebSocketMessage<?> webSocketMessage, WebSocketSession webSocketSession) {
            this.webSocketMessage = webSocketMessage;
            this.webSocketSession = webSocketSession;
        }

        private <T> MessageContext handle(Consumer<WebSocketSession> consumer) {
            if (jrh.game.service.websocket.client.ClientWebSocketMessageTypes.END_TURN.equals(webSocketMessage.getType())) {
                wasHandled = true;
                consumer.accept(webSocketSession);
            }
            return this;
        }

        private <T> MessageContext handle(WebSocketMessageType<T> webSocketMessageType, BiConsumer<WebSocketSession, T> consumer) {
            if (webSocketMessageType.equals(webSocketMessage.getType())) {
                WebSocketMessage<T> message = cast(webSocketMessage, webSocketMessageType.getPayloadType());
                wasHandled = true;
                consumer.accept(webSocketSession, message.getPayload());
            }
            return this;
        }

        private void end() {
            if (!wasHandled) {
                logger.warn("Unhandled WebSocketMessage={} for session={}", webSocketMessage, webSocketSession);
            }
        }

        @SuppressWarnings("unchecked")
        private static <T> WebSocketMessage<T> cast(WebSocketMessage<?> webSocketMessage, Class<T> clazz) {
            if (!webSocketMessage.getType().getPayloadType().equals(clazz)) {
                throw new IllegalArgumentException(
                    String.format("Expected payload of class %s for websocket message %s", clazz, webSocketMessage));
            }
            return (WebSocketMessage<T>) webSocketMessage;
        }
    }

}
