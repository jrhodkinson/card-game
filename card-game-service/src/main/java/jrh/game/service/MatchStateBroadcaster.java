package jrh.game.service;

import jrh.game.api.Match;
import jrh.game.api.event.CardResolved;
import jrh.game.common.event.EventHandler;
import jrh.game.common.event.Subscribe;
import jrh.game.service.dto.MatchDto;

import static jrh.game.service.WebSocketMessageTypes.MATCH_STATE;

public class MatchStateBroadcaster implements EventHandler {

    private final WebSocketConnectionManager webSocketConnectionManager;

    public MatchStateBroadcaster(WebSocketConnectionManager webSocketConnectionManager) {
        this.webSocketConnectionManager = webSocketConnectionManager;
    }

    @Subscribe
    public void cardResolved(CardResolved cardResolved, Match match) {
        webSocketConnectionManager.broadcast(new WebSocketMessage<>(MATCH_STATE, MatchDto.fromMatch(match)));
    }

}
