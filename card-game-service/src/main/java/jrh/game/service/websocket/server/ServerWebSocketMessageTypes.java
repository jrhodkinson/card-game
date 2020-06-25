package jrh.game.service.websocket;

import jrh.game.service.websocket.server.dto.MatchDto;

public class ServerWebSocketMessageTypes extends WebSocketMessageTypes {
    public static final WebSocketMessageType<Long> PING = WebSocketMessageType.of("ping", Long.class);
    public static final WebSocketMessageType<MatchDto> MATCH_STATE = WebSocketMessageType.of("matchState",
            MatchDto.class);
}
