package jrh.game.service.websocket.server;

import jrh.game.common.User;
import jrh.game.service.websocket.WebSocketMessage;
import jrh.game.service.websocket.server.dto.MatchDto;
import org.joda.time.DateTimeUtils;

import static jrh.game.service.websocket.ServerWebSocketMessageTypes.MATCH_ENDED;
import static jrh.game.service.websocket.ServerWebSocketMessageTypes.MATCH_STATE;
import static jrh.game.service.websocket.ServerWebSocketMessageTypes.PING;

public class ServerWebSocketMessages {

    public static WebSocketMessage<Long> ping() {
        return new WebSocketMessage<>(PING, DateTimeUtils.currentTimeMillis());
    }

    public static WebSocketMessage<MatchDto> matchState(MatchDto matchDto) {
        return new WebSocketMessage<>(MATCH_STATE, matchDto);
    }

    public static WebSocketMessage<User> matchEnded(User user) {
        return new WebSocketMessage<>(MATCH_ENDED, user);
    }
}
