package jrh.game.service.websocket;

import jrh.game.service.dto.MatchDto;

public class WebSocketMessageTypes {

    public static final WebSocketMessageType<Long> PING = new WebSocketMessageType<>("ping", Long.class);

    public static final WebSocketMessageType<MatchDto> MATCH_STATE = new WebSocketMessageType<>("matchState",
            MatchDto.class);

}
