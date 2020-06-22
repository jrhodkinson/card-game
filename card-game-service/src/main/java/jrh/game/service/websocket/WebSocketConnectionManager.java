package jrh.game.service.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;
import jrh.game.common.ObjectMapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.Collections.emptyList;
import static jrh.game.service.websocket.WebSocketMessageTypes.PING;
import static jrh.game.service.websocket.WebSocketMessageTypes.PONG;

public class WebSocketConnectionManager {

    private static final Logger logger = LogManager.getLogger(WebSocketConnectionManager.class);

    private final Javalin javalin;
    private final String route;
    private final List<WsContext> webSocketClients = new ArrayList<>();
    private final List<Supplier<Optional<? extends WebSocketMessage<?>>>> welcomeMessageSuppliers = new ArrayList<>();
    private final Map<WebSocketMessageType<?>, List<Consumer<WebSocketMessage>>> websocketMessageSubscribers = new HashMap<>();
    private final ObjectMapper objectMapper = ObjectMapperFactory.create();

    public WebSocketConnectionManager(Javalin javalin, String route) {
        this.javalin = javalin;
        this.route = route;
    }

    public void start() {
        javalin.ws(route, ws -> {
            ws.onConnect(this::handleConnect);
            ws.onMessage(this::handleMessage);
            ws.onClose(this::handleClose);
        });
    }

    public void broadcast(WebSocketMessage<?> webSocketMessage) {
        try {
            String messageJson = objectMapper.writeValueAsString(webSocketMessage);
            if (!webSocketMessage.getType().equals(PING)) {
                logger.debug("Broadcasting to {} clients: message={}", webSocketClients.size(), messageJson);
            }
            webSocketClients.forEach(ctx -> ctx.send(messageJson));
        } catch (JsonProcessingException e) {
            logger.error("Failed to broadcast websocket message: could not convert message={} to json",
                    webSocketMessage, e);
        }
    }

    public void addWelcomeMessage(Supplier<Optional<? extends WebSocketMessage<?>>> welcomeMessageSupplier) {
        logger.info("Added welcome message supplier {}", welcomeMessageSupplier);
        this.welcomeMessageSuppliers.add(welcomeMessageSupplier);
    }

    public <T> void subscribe(WebSocketMessageType<T> messageType, Consumer<WebSocketMessage<T>> consumer) {
        List<Consumer<WebSocketMessage>> consumers = websocketMessageSubscribers.getOrDefault(messageType, new ArrayList<>());
        consumers.add(consumer::accept);
        websocketMessageSubscribers.put(messageType, consumers);
    }

    private void handleConnect(WsConnectContext wsConnectContext) {
        logger.info("Websocket connected: session={}", wsConnectContext.getSessionId());
        webSocketClients.add(wsConnectContext);
        logger.info("Sending {} welcome message(s)", welcomeMessageSuppliers.size());
        welcomeMessageSuppliers.forEach(supplier -> supplier.get().ifPresent(wsConnectContext::send));
    }

    private void handleMessage(WsMessageContext wsMessageContext) {
        try {
            WebSocketMessage<?> webSocketMessage = objectMapper.readValue(wsMessageContext.message(), WebSocketMessage.class);
            if (!webSocketMessage.getType().equals(PONG)) {
                logger.debug("RX message={} for session={}", webSocketMessage, wsMessageContext.getSessionId());
            }
            websocketMessageSubscribers.getOrDefault(webSocketMessage.getType(), emptyList())
                .forEach(consumer -> consumer.accept(webSocketMessage));
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse message={} for session={}", wsMessageContext.message(), wsMessageContext.session, e);
        }
    }

    private void handleClose(WsCloseContext wsCloseContext) {
        logger.info("Websocket closed: session={}", wsCloseContext.getSessionId());
        webSocketClients.remove(wsCloseContext);
    }
}
