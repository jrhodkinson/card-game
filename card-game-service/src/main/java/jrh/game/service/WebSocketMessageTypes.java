package jrh.game.service;

import jrh.game.service.dto.MatchDto;

public class WebSocketMessageTypes {

    public static final WebSocketMessageType<MatchDto> MATCH_STATE = new WebSocketMessageType<>("matchState",
            MatchDto.class);

}
