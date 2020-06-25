package jrh.game.service.websocket.server;

import jrh.game.service.dto.MatchDto;
import jrh.game.service.websocket.WebSocketMessage;
import org.joda.time.DateTimeUtils;

import static jrh.game.service.websocket.ServerWebSocketMessageTypes.MATCH_STATE;
import static jrh.game.service.websocket.ServerWebSocketMessageTypes.PING;

public class ServerWebSocketMessages {

    public static WebSocketMessage<Long> ping() {
        return new WebSocketMessage<>(PING, DateTimeUtils.currentTimeMillis());
    }

    public static WebSocketMessage<MatchDto> matchState(MatchDto matchDto) {
        return new WebSocketMessage<>(MATCH_STATE, matchDto);
    }
}
