package jrh.game.service;

import jrh.game.api.Match;
import jrh.game.api.event.Subscribe;
import jrh.game.api.event.impl.CardResolved;
import jrh.game.api.event.impl.MatchStarted;
import jrh.game.common.EventHandler;
import jrh.game.service.dto.MatchDto;

import java.util.Optional;

import static jrh.game.service.WebSocketMessageTypes.MATCH_STATE;

public class MatchStateBroadcaster implements EventHandler {

    private final WebSocketConnectionManager webSocketConnectionManager;
    private MatchDto latestMatchState;

    public MatchStateBroadcaster(WebSocketConnectionManager webSocketConnectionManager) {
        this.webSocketConnectionManager = webSocketConnectionManager;
        webSocketConnectionManager.addWelcomeMessage(this::matchStateMessage);
    }

    @Subscribe
    private void matchStarted(MatchStarted matchStarted, Match match) {
        latestMatchState = MatchDto.fromMatch(match);
        broadcastMatchState();
    }

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match) {
        latestMatchState = MatchDto.fromMatch(match);
        broadcastMatchState();
    }

    private void broadcastMatchState() {
        matchStateMessage().ifPresent(webSocketConnectionManager::broadcast);
    }

    private Optional<WebSocketMessage<?>> matchStateMessage() {
        if (latestMatchState == null) {
            return Optional.empty();
        }
        return Optional.of(new WebSocketMessage<>(MATCH_STATE, latestMatchState));
    }
}
