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
    private boolean matchIsOver = false;

    public MatchStateBroadcaster(WebSocketConnectionManager webSocketConnectionManager) {
        this.webSocketConnectionManager = webSocketConnectionManager;
        webSocketConnectionManager.addWelcomeMessage(this::matchStateMessage);
    }

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match) {
        broadcastFullMatchState(match);
    }

    @Subscribe
    private void cardDestroyed(CardDestroyed cardDestroyed, Match match) {
        broadcastFullMatchState(match);
    }

    @Subscribe
    private void cardPurchased(CardPurchased cardPurchased, Match match) {
        broadcastFullMatchState(match);
    }

    @Subscribe
    private void matchStarted(MatchStarted matchStarted, Match match) {
        broadcastFullMatchState(match);
    }

    @Subscribe
    private void turnEnded(TurnEnded turnEnded, Match match) {
        broadcastFullMatchState(match);
    }

    @Subscribe
    private void matchEnded(MatchEnded matchEnded) {
        webSocketConnectionManager.broadcast(ServerWebSocketMessages.matchEnded(matchEnded.getWinner()));
        matchIsOver = true;
    }

    private void broadcastFullMatchState(Match match) {
        latestMatchState = MatchDto.fromMatch(match);
        matchStateMessage().ifPresent(webSocketConnectionManager::broadcast);
    }

    private Optional<WebSocketMessage<MatchDto>> matchStateMessage() {
        if (matchIsOver) {
            return Optional.empty();
        }
        return Optional.ofNullable(latestMatchState).map(ServerWebSocketMessages::matchState);
    }
}
