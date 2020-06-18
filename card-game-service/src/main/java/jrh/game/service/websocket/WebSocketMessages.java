package jrh.game.service.websocket;

import jrh.game.service.dto.MatchDto;
import org.joda.time.DateTimeUtils;

import static jrh.game.service.websocket.WebSocketMessageTypes.MATCH_STATE;
import static jrh.game.service.websocket.WebSocketMessageTypes.PING;

public class WebSocketMessages {

    public static WebSocketMessage<Long> ping() {
        return new WebSocketMessage<>(PING, DateTimeUtils.currentTimeMillis());
    }

    public static WebSocketMessage<MatchDto> matchState(MatchDto matchDto) {
        return new WebSocketMessage<>(MATCH_STATE, matchDto);
    }
}
