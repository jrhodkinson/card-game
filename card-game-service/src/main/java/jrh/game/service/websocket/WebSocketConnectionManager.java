package jrh.game.service.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import jrh.game.common.ObjectMapperFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static jrh.game.service.websocket.WebSocketMessageTypes.PING;

public class WebSocketConnectionManager {

    private static final Logger logger = LogManager.getLogger(WebSocketConnectionManager.class);

    private final Javalin javalin;
    private final String route;
    private final List<WsContext> webSocketClients = new ArrayList<>();
    private final List<Supplier<Optional<? extends WebSocketMessage<?>>>> welcomeMessageSuppliers = new ArrayList<>();
    private final ObjectMapper objectMapper = ObjectMapperFactory.create();

    public WebSocketConnectionManager(Javalin javalin, String route) {
        this.javalin = javalin;
        this.route = route;
    }

    public void start() {
        javalin.ws(route, ws -> {
            ws.onConnect(this::handleConnect);
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

    private void handleConnect(WsConnectContext wsConnectContext) {
        logger.info("Websocket connected: session={}", wsConnectContext.getSessionId());
        webSocketClients.add(wsConnectContext);
        logger.info("Sending {} welcome message(s)", welcomeMessageSuppliers.size());
        welcomeMessageSuppliers.forEach(supplier -> supplier.get().ifPresent(wsConnectContext::send));
    }

    private void handleClose(WsCloseContext wsCloseContext) {
        logger.info("Websocket closed: session={}", wsCloseContext.getSessionId());
        webSocketClients.remove(wsCloseContext);
    }
}
