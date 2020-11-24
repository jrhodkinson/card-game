package jrh.game.service.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.common.User;
import jrh.game.common.account.AccountId;
import jrh.game.service.account.Accounts;
import jrh.game.service.lobby.ActiveMatch;
import jrh.game.service.lobby.MatchManager;
import jrh.game.service.websocket.server.SingleMatchStateBroadcaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

import static jrh.game.service.Attributes.ACCOUNT_ID;
import static jrh.game.service.websocket.client.ClientWebSocketMessageTypes.PONG;
import static jrh.game.service.websocket.server.ServerWebSocketMessageTypes.PING;

public class WebSocketConnectionManager {

    private static final Logger logger = LogManager.getLogger(WebSocketConnectionManager.class);
    private static final String PARAM_MATCH_ID = "matchId";

    private final Javalin javalin;
    private final Accounts accounts;
    private final MatchManager matchManager;
    private final WebSocketMessageHandler webSocketMessageHandler;
    private final Map<UUID, SingleMatchStateBroadcaster> singleMatchStateBroadcasters = new ConcurrentHashMap<>();
    private final Map<String, WsContext> webSocketClientsBySessionId = new ConcurrentHashMap<>();
    private final Map<String, WebSocketSession> webSocketSessionsBySessionId = new ConcurrentHashMap<>();
    private final Map<UUID, List<Supplier<Optional<? extends WebSocketMessage<?>>>>> welcomeMessageSuppliersByMatchId = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = ObjectMapperFactory.create();

    public WebSocketConnectionManager(Javalin javalin, Accounts accounts, MatchManager matchManager,
            WebSocketMessageHandler webSocketMessageHandler) {
        this.javalin = javalin;
        this.accounts = accounts;
        this.matchManager = matchManager;
        this.webSocketMessageHandler = webSocketMessageHandler;
    }

    public void start() {
        javalin.ws(String.format("match/:%s", PARAM_MATCH_ID), ws -> {
            ws.onConnect(this::handleConnect);
            ws.onMessage(this::handleMessage);
            ws.onClose(this::handleClose);
        });
    }

    public void broadcast(UUID matchId, WebSocketMessage<?> webSocketMessage) {
        webSocketSessionsBySessionId.values().stream().filter(s -> matchId.equals(s.getMatch().getId()))
                .forEach(s -> send(s, webSocketMessage));
    }

    public void broadcastAll(WebSocketMessage<?> webSocketMessage) {
        webSocketSessionsBySessionId.values().forEach(s -> send(s, webSocketMessage));
    }

    public void send(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
        if (!webSocketMessage.getType().equals(PING)) {
            logger.debug("TX to session={}, message={}", webSocketSession.getSessionId(), webSocketMessage);
        }
        webSocketClientsBySessionId.get(webSocketSession.getSessionId()).send(webSocketMessage);
    }

    public void addWelcomeMessage(UUID matchId,
            Supplier<Optional<? extends WebSocketMessage<?>>> welcomeMessageSupplier) {
        logger.info("Added welcome message supplier {} for matchId={}", welcomeMessageSupplier, matchId);
        welcomeMessageSuppliersByMatchId.computeIfAbsent(matchId, m -> new CopyOnWriteArrayList<>())
                .add(welcomeMessageSupplier);
    }

    private void handleConnect(WsConnectContext wsConnectContext) {
        AccountId accountId = wsConnectContext.attribute(ACCOUNT_ID);
        logger.info("Websocket connected: accountId={}, session={}", accountId, wsConnectContext.getSessionId());
        String sessionId = wsConnectContext.getSessionId();
        UUID matchId = wsConnectContext.pathParam(PARAM_MATCH_ID, UUID.class).get();
        ActiveMatch match = matchManager.getMatchById(matchId);
        if (match != null) {
            User user = accounts.getAccount(accountId).toUser();
            WebSocketSession webSocketSession = new WebSocketSession(sessionId, match, user);
            webSocketClientsBySessionId.put(sessionId, wsConnectContext);
            webSocketSessionsBySessionId.put(sessionId, webSocketSession);
            singleMatchStateBroadcasters.computeIfAbsent(matchId, m -> {
                SingleMatchStateBroadcaster broadcaster = new SingleMatchStateBroadcaster(this, m);
                match.getEventBus().register(broadcaster);
                return broadcaster;
            });
            List<Supplier<Optional<? extends WebSocketMessage<?>>>> welcomeMessageSuppliers = welcomeMessageSuppliersByMatchId
                    .getOrDefault(matchId, Collections.emptyList());
            logger.info("Sending {} welcome message(s)", welcomeMessageSuppliers.size());
            welcomeMessageSuppliers.forEach(supplier -> supplier.get().ifPresent(wsConnectContext::send));
        }
    }

    private void handleMessage(WsMessageContext wsMessageContext) {
        logger.info("{}", wsMessageContext.attributeMap());
        WebSocketMessage<?> webSocketMessage = wsMessageContext.message(WebSocketMessage.class);
        if (!webSocketMessage.getType().equals(PONG)) {
            logger.debug("RX message={} for session={}", webSocketMessage, wsMessageContext.getSessionId());
            WebSocketSession webSocketSession = webSocketSessionsBySessionId.get(wsMessageContext.getSessionId());
            webSocketMessageHandler.handleMessage(webSocketSession, webSocketMessage);
        }
    }

    private void handleClose(WsCloseContext wsCloseContext) {
        logger.info("Websocket closed: session={}", wsCloseContext.getSessionId());
        String sessionId = wsCloseContext.getSessionId();
        webSocketClientsBySessionId.remove(sessionId);
        webSocketSessionsBySessionId.remove(sessionId);
    }
}
