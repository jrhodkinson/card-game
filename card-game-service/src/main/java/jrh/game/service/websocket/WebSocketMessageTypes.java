package jrh.game.service.websocket;

import jrh.game.common.InstanceId;
import jrh.game.common.User;
import jrh.game.service.dto.MatchDto;

import java.util.HashMap;
import java.util.Map;

public class WebSocketMessageTypes {

    public static final WebSocketMessageType<Long> PING = WebSocketMessageType.of("ping", Long.class);
    public static final WebSocketMessageType<Long> PONG = WebSocketMessageType.of("pong", Long.class);
    public static final WebSocketMessageType<User> LOGIN = WebSocketMessageType.of("login", User.class);
    public static final WebSocketMessageType<NoPayload> END_TURN = WebSocketMessageType.emptyPayload("turn/end");
    public static final WebSocketMessageType<MatchDto> MATCH_STATE = WebSocketMessageType.of("matchState", MatchDto.class);
    public static final WebSocketMessageType<InstanceId> PLAY_CARD = WebSocketMessageType.of("card/play", InstanceId.class);
    public static final WebSocketMessageType<InstanceId> BUY_CARD = WebSocketMessageType.of("card/buy", InstanceId.class);

    private static final Map<String, WebSocketMessageType<?>> types = new HashMap<>();
    static {
        types.put(PING.toString(), PING);
        types.put(PONG.toString(), PONG);
        types.put(LOGIN.toString(), LOGIN);
        types.put(END_TURN.toString(), END_TURN);
        types.put(MATCH_STATE.toString(), MATCH_STATE);
        types.put(PLAY_CARD.toString(), PLAY_CARD);
        types.put(BUY_CARD.toString(), BUY_CARD);
    }

    public static WebSocketMessageType<?> get(String messageType) {
        return types.get(messageType);
    }
}
