package jrh.game.service.websocket.server;

import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardDestroyed;
import jrh.game.api.event.CardPurchased;
import jrh.game.api.event.CardResolved;
import jrh.game.api.event.MatchEnded;
import jrh.game.api.event.MatchStarted;
import jrh.game.api.event.TurnEnded;
import jrh.game.common.EventHandler;
import jrh.game.service.websocket.WebSocketConnectionManager;
import jrh.game.service.websocket.WebSocketMessage;
import jrh.game.service.websocket.server.dto.MatchDto;

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
        broadcaseFullMatchState(match);
    }

    @Subscribe
    private void cardDestroyed(CardDestroyed cardDestroyed, Match match) {
        broadcaseFullMatchState(match);
    }

    @Subscribe
    private void cardPurchased(CardPurchased cardPurchased, Match match) {
        broadcaseFullMatchState(match);
    }

    @Subscribe
    private void matchStarted(MatchStarted matchStarted, Match match) {
        broadcaseFullMatchState(match);
    }

    @Subscribe
    private void turnEnded(TurnEnded turnEnded, Match match) {
        broadcaseFullMatchState(match);
    }

    @Subscribe
    private void matchEnded(MatchEnded matchEnded) {
        webSocketConnectionManager.broadcast(ServerWebSocketMessages.matchEnded(matchEnded.getWinner()));
    }

    private void broadcaseFullMatchState(Match match) {
        latestMatchState = MatchDto.fromMatch(match);
        matchStateMessage().ifPresent(webSocketConnectionManager::broadcast);
    }

    private Optional<WebSocketMessage<MatchDto>> matchStateMessage() {
        return Optional.ofNullable(latestMatchState).map(ServerWebSocketMessages::matchState);
    }
}
