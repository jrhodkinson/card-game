package jrh.game.service.websocket.server;

import jrh.game.api.Event;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.SubscribeAll;
import jrh.game.api.event.CardDestroyed;
import jrh.game.api.event.CardGained;
import jrh.game.api.event.CardPurchased;
import jrh.game.api.event.CardResolved;
import jrh.game.api.event.EventHandlerRegistered;
import jrh.game.api.event.MatchEnded;
import jrh.game.api.event.MatchStarted;
import jrh.game.api.event.MoneyChanged;
import jrh.game.api.event.PlayerTookDamage;
import jrh.game.api.event.TurnEnded;
import jrh.game.common.EventHandler;
import jrh.game.common.User;
import jrh.game.service.websocket.WebSocketConnectionManager;
import jrh.game.service.websocket.WebSocketMessage;
import jrh.game.service.websocket.server.dto.MatchDto;

import java.util.Optional;
import java.util.UUID;

public class SingleMatchStateBroadcaster implements EventHandler {

    private final WebSocketConnectionManager webSocketConnectionManager;
    private final UUID matchId;
    private MatchDto latestMatchState;
    private User winner;

    public SingleMatchStateBroadcaster(WebSocketConnectionManager webSocketConnectionManager, UUID matchId) {
        this.webSocketConnectionManager = webSocketConnectionManager;
        this.matchId = matchId;
        webSocketConnectionManager.addWelcomeMessage(matchId, this::matchStateMessage);
    }

    @SubscribeAll({EventHandlerRegistered.class, CardResolved.class, CardDestroyed.class, CardPurchased.class, CardGained.class,
        PlayerTookDamage.class, MatchStarted.class, TurnEnded.class, MoneyChanged.class})
    private void matchStateChanged(Event event, Match match) {
        broadcastFullMatchState(match);
    }

    @Subscribe
    private void matchEnded(MatchEnded matchEnded) {
        winner = matchEnded.getWinner();
        webSocketConnectionManager.broadcast(matchId, ServerWebSocketMessages.matchEnded(matchEnded.getWinner()));
    }

    private void broadcastFullMatchState(Match match) {
        latestMatchState = MatchDto.fromMatch(match);
        matchStateMessage().ifPresent(m -> webSocketConnectionManager.broadcast(matchId, m));
    }

    private Optional<WebSocketMessage<?>> matchStateMessage() {
        if (winner != null) {
            return Optional.of(ServerWebSocketMessages.matchEnded(winner));
        }
        return Optional.ofNullable(latestMatchState).map(ServerWebSocketMessages::matchState);
    }
}
