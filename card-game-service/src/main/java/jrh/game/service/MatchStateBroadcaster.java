package jrh.game.service;

import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardPurchased;
import jrh.game.api.event.CardResolved;
import jrh.game.api.event.MatchStarted;
import jrh.game.api.event.TurnEnded;
import jrh.game.common.EventHandler;
import jrh.game.service.websocket.server.dto.MatchDto;
import jrh.game.service.websocket.WebSocketConnectionManager;
import jrh.game.service.websocket.WebSocketMessage;
import jrh.game.service.websocket.server.ServerWebSocketMessages;

import java.util.Optional;

public class MatchStateBroadcaster implements EventHandler {

    private final WebSocketConnectionManager webSocketConnectionManager;
    private MatchDto latestMatchState;

    public MatchStateBroadcaster(WebSocketConnectionManager webSocketConnectionManager) {
        this.webSocketConnectionManager = webSocketConnectionManager;
        webSocketConnectionManager.addWelcomeMessage(this::matchStateMessage);
    }

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match) {
        broadcastMatchState(match);
    }

    @Subscribe
    private void cardPurchased(CardPurchased cardPurchased, Match match) {
        broadcastMatchState(match);
    }

    @Subscribe
    private void matchStarted(MatchStarted matchStarted, Match match) {
        broadcastMatchState(match);
    }

    @Subscribe
    private void turnEnded(TurnEnded turnEnded, Match match) {
        broadcastMatchState(match);
    }

    private void broadcastMatchState(Match match) {
        latestMatchState = MatchDto.fromMatch(match);
        matchStateMessage().ifPresent(webSocketConnectionManager::broadcast);
    }

    private Optional<WebSocketMessage<MatchDto>> matchStateMessage() {
        return Optional.ofNullable(latestMatchState).map(ServerWebSocketMessages::matchState);
    }
}
