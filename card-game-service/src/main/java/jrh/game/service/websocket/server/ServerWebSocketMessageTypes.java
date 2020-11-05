package jrh.game.service.websocket.server;

import jrh.game.common.User;
import jrh.game.service.websocket.WebSocketMessageType;
import jrh.game.service.websocket.WebSocketMessageTypes;
import jrh.game.service.websocket.server.dto.MatchDto;

public class ServerWebSocketMessageTypes extends WebSocketMessageTypes {
    public static final WebSocketMessageType<Long> PING = WebSocketMessageType.of("ping", Long.class);
    public static final WebSocketMessageType<MatchDto> MATCH_STATE = WebSocketMessageType.of("matchState",
            MatchDto.class);
    public static final WebSocketMessageType<User> MATCH_ENDED = WebSocketMessageType.of("matchEnded", User.class);
}
