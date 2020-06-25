package jrh.game.service.websocket;

import jrh.game.api.action.BuyCard;
import jrh.game.api.action.EndTurn;
import jrh.game.api.action.PlayCard;
import jrh.game.common.InstanceId;
import jrh.game.common.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static jrh.game.service.websocket.client.ClientWebSocketMessageTypes.BUY_CARD;
import static jrh.game.service.websocket.client.ClientWebSocketMessageTypes.END_TURN;
import static jrh.game.service.websocket.client.ClientWebSocketMessageTypes.LOGIN;
import static jrh.game.service.websocket.client.ClientWebSocketMessageTypes.PLAY_CARD;

public class WebSocketMessageHandler {

    private static final Logger logger = LogManager.getLogger(WebSocketMessageHandler.class);

    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
        handle(webSocketMessage, webSocketSession).forType(LOGIN).perform(this::login).forType(END_TURN)
                .perform(this::endTurn).forType(PLAY_CARD).perform(this::playCard).forType(BUY_CARD)
                .perform(this::buyCard).end();
    }

    private void login(WebSocketSession webSocketSession, User user) {
        logger.info("Setting user={} for session={}", user, webSocketSession);
        webSocketSession.setUser(user);
    }

    private void endTurn(WebSocketSession webSocketSession) {
        if (webSocketSession.getUser() != null) {
            EndTurn endTurn = new EndTurn(webSocketSession.getUser());
            logger.info("TX endTurn={}", endTurn);
            webSocketSession.getMatch().getActionHandler().accept(endTurn);
        }
    }

    private void playCard(WebSocketSession webSocketSession, InstanceId instanceId) {
        PlayCard playCard = new PlayCard(webSocketSession.getUser(), instanceId, null);
        logger.info("TX playCard={}", playCard);
        webSocketSession.getMatch().getActionHandler().accept(playCard);
    }

    private void buyCard(WebSocketSession webSocketSession, InstanceId instanceId) {
        BuyCard buyCard = new BuyCard(webSocketSession.getUser(), instanceId);
        logger.info("TX buyCard={}", buyCard);
        webSocketSession.getMatch().getActionHandler().accept(buyCard);
    }

    private static MessageContext handle(WebSocketMessage<?> webSocketMessage, WebSocketSession webSocketSession) {
        return new MessageContext(webSocketMessage, webSocketSession);
    }

    @SuppressWarnings("unchecked")
    private static <T> WebSocketMessage<T> cast(WebSocketMessage<?> webSocketMessage, Class<T> clazz) {
        if (!webSocketMessage.getType().getPayloadType().equals(clazz)) {
            throw new IllegalArgumentException(
                    String.format("Expected payload of class %s for websocket message %s", clazz, webSocketMessage));
        }
        return (WebSocketMessage<T>) webSocketMessage;
    }

    private static class MessageContext {

        private final WebSocketMessage<?> webSocketMessage;
        private final WebSocketSession webSocketSession;
        private boolean wasHandled = false;

        private MessageContext(WebSocketMessage<?> webSocketMessage, WebSocketSession webSocketSession) {
            this.webSocketMessage = webSocketMessage;
            this.webSocketSession = webSocketSession;
        }

        private <T> SpecificTypeHandler<T> forType(WebSocketMessageType<T> webSocketMessageType) {
            if (webSocketMessageType.equals(webSocketMessage.getType())) {
                WebSocketMessage<T> message = cast(webSocketMessage, webSocketMessageType.getPayloadType());
                wasHandled = true;
                return new SpecificTypeHandler<>(this, message.getPayload());
            } else {
                return new SpecificTypeHandler<>(this);
            }
        }

        private void end() {
            if (!wasHandled) {
                logger.warn("Unhandled WebSocketMessage={} for session={}", webSocketMessage, webSocketSession);
            }
        }
    }

    private static class SpecificTypeHandler<T> {

        private final MessageContext messageContext;
        private T payload;
        private boolean isPresent = false;

        private SpecificTypeHandler(MessageContext messageContext) {
            this.messageContext = messageContext;
        }

        private SpecificTypeHandler(MessageContext messageContext, T payload) {
            this.messageContext = messageContext;
            this.payload = payload;
            this.isPresent = true;
        }

        private MessageContext perform(Consumer<WebSocketSession> consumer) {
            if (isPresent) {
                consumer.accept(messageContext.webSocketSession);
            }
            return messageContext;
        }

        private MessageContext perform(BiConsumer<WebSocketSession, T> consumer) {
            if (isPresent) {
                consumer.accept(messageContext.webSocketSession, payload);
            }
            return messageContext;
        }
    }
}
