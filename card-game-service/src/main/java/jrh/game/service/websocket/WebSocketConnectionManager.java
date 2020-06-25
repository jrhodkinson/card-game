package jrh.game.service.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;
import jrh.game.api.Match;
import jrh.game.common.ObjectMapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static jrh.game.service.websocket.ServerWebSocketMessageTypes.PING;
import static jrh.game.service.websocket.client.ClientWebSocketMessageTypes.PONG;

public class WebSocketConnectionManager {

    private static final Logger logger = LogManager.getLogger(WebSocketConnectionManager.class);
    private static final String ROUTE = "match";

    private final Javalin javalin;
    private final Match match;
    private final WebSocketMessageHandler webSocketMessageHandler;
    private final Map<String, WsContext> webSocketClientsBySessionId = new HashMap<>();
    private final Map<String, WebSocketSession> webSocketSessionsBySessionId = new HashMap<>();
    private final List<Supplier<Optional<? extends WebSocketMessage<?>>>> welcomeMessageSuppliers = new ArrayList<>();
    private final ObjectMapper objectMapper = ObjectMapperFactory.create();

    public WebSocketConnectionManager(Javalin javalin, Match match, WebSocketMessageHandler webSocketMessageHandler) {
        this.javalin = javalin;
        this.match = match;
        this.webSocketMessageHandler = webSocketMessageHandler;
    }

    public void start() {
        javalin.ws("match", ws -> {
            ws.onConnect(this::handleConnect);
            ws.onMessage(this::handleMessage);
            ws.onClose(this::handleClose);
        });
    }

    public void broadcast(WebSocketMessage<?> webSocketMessage) {
        try {
            String messageJson = objectMapper.writeValueAsString(webSocketMessage);
            if (!webSocketMessage.getType().equals(PING)) {
                logger.debug("Broadcasting to {} clients: message={}", webSocketClientsBySessionId.size(), messageJson);
            }
            webSocketClientsBySessionId.values().forEach(ctx -> ctx.send(messageJson));
        } catch (JsonProcessingException e) {
            logger.error("Failed to broadcast websocket message: could not convert message={} to json",
                    webSocketMessage, e);
        }
    }

    public void addWelcomeMessage(Supplier<Optional<? extends WebSocketMessage<?>>> welcomeMessageSupplier) {
        logger.info("Added welcome message supplier {}", welcomeMessageSupplier);
        this.welcomeMessageSuppliers.add(welcomeMessageSupplier);
    }

    private void handleConnect(WsConnectContext wsConnectContext) {
        logger.info("Websocket connected: session={}", wsConnectContext.getSessionId());
        String sessionId = wsConnectContext.getSessionId();
        WebSocketSession webSocketSession = new WebSocketSession(sessionId, match);
        webSocketClientsBySessionId.put(sessionId, wsConnectContext);
        webSocketSessionsBySessionId.put(sessionId, webSocketSession);
        logger.info("Sending {} welcome message(s)", welcomeMessageSuppliers.size());
        welcomeMessageSuppliers.forEach(supplier -> supplier.get().ifPresent(wsConnectContext::send));
    }

    private void handleMessage(WsMessageContext wsMessageContext) {
        try {
            WebSocketMessage<?> webSocketMessage = objectMapper.readValue(wsMessageContext.message(),
                    WebSocketMessage.class);
            if (!webSocketMessage.getType().equals(PONG)) {
                logger.debug("RX message={} for session={}", webSocketMessage, wsMessageContext.getSessionId());
                WebSocketSession webSocketSession = webSocketSessionsBySessionId.get(wsMessageContext.getSessionId());
                webSocketMessageHandler.handleMessage(webSocketSession, webSocketMessage);
            }
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse message={} for session={}", wsMessageContext.message(),
                    wsMessageContext.session, e);
        }
    }

    private void handleClose(WsCloseContext wsCloseContext) {
        logger.info("Websocket closed: session={}", wsCloseContext.getSessionId());
        String sessionId = wsCloseContext.getSessionId();
        webSocketClientsBySessionId.remove(sessionId);
        webSocketSessionsBySessionId.remove(sessionId);
    }
}
