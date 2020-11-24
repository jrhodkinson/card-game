package jrh.game.service.websocket.server;

import jrh.game.common.User;
import jrh.game.service.websocket.WebSocketMessage;
import jrh.game.service.websocket.server.dto.MatchDto;

import java.time.Clock;

import static jrh.game.service.websocket.server.ServerWebSocketMessageTypes.MATCH_ENDED;
import static jrh.game.service.websocket.server.ServerWebSocketMessageTypes.MATCH_STATE;
import static jrh.game.service.websocket.server.ServerWebSocketMessageTypes.PING;

public class ServerWebSocketMessages {

    public static WebSocketMessage<Long> ping() {
        return new WebSocketMessage<>(PING, Clock.systemUTC().millis());
    }

    public static WebSocketMessage<MatchDto> matchState(MatchDto matchDto) {
        return new WebSocketMessage<>(MATCH_STATE, matchDto);
    }

    public static WebSocketMessage<User> matchEnded(User user) {
        return new WebSocketMessage<>(MATCH_ENDED, user);
    }
}
