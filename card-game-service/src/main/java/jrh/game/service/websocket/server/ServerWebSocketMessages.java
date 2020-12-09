package jrh.game.service.websocket.server;

import jrh.game.common.User;
import jrh.game.service.websocket.WebSocketMessage;
import jrh.game.service.websocket.server.dto.MatchDto;

import java.time.Clock;
import java.time.Instant;

import static jrh.game.service.websocket.server.ServerWebSocketMessageTypes.MATCH_ENDED;
import static jrh.game.service.websocket.server.ServerWebSocketMessageTypes.MATCH_STATE;
import static jrh.game.service.websocket.server.ServerWebSocketMessageTypes.PING;
import static jrh.game.service.websocket.server.ServerWebSocketMessageTypes.TURN_WILL_END_AT;

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

    public static WebSocketMessage<Long> turnWillEndAt(Instant time) {
        return new WebSocketMessage<>(TURN_WILL_END_AT, time.toEpochMilli());
    }
}
