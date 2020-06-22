package jrh.game.service.websocket;

import jrh.game.common.InstanceId;
import jrh.game.service.dto.MatchDto;

import java.util.HashMap;
import java.util.Map;

public class WebSocketMessageTypes {

    public static final WebSocketMessageType<Long> PING = new WebSocketMessageType<>("ping", Long.class);
    public static final WebSocketMessageType<Long> PONG = new WebSocketMessageType<>("pong", Long.class);

    public static final WebSocketMessageType<MatchDto> MATCH_STATE = new WebSocketMessageType<>("matchState",
            MatchDto.class);

    public static final WebSocketMessageType<InstanceId> PLAY_CARD = new WebSocketMessageType<>("card/play", InstanceId.class);

    private static final Map<String, WebSocketMessageType<?>> types = new HashMap<>();
    static {
        types.put(PING.toString(), PING);
        types.put(PONG.toString(), PONG);
        types.put(MATCH_STATE.toString(), MATCH_STATE);
        types.put(PLAY_CARD.toString(), PLAY_CARD);
    }

    public static WebSocketMessageType<?> get(String messageType) {
        return types.get(messageType);
    }
}
