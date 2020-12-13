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
import jrh.game.api.event.TurnStarted;
import jrh.game.api.event.TurnWillEndAt;
import jrh.game.common.EventHandler;
import jrh.game.common.User;
import jrh.game.service.websocket.WebSocketConnectionManager;
import jrh.game.service.websocket.WebSocketMessage;
import jrh.game.service.websocket.server.dto.MatchDto;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class SingleMatchStateBroadcaster implements EventHandler {

    private final WebSocketConnectionManager webSocketConnectionManager;
    private final MatchDto.Factory matchDtoFactory;
    private final UUID matchId;
    private Instant turnWillEndAt;
    private MatchDto latestMatchState;
    private User winner;

    public SingleMatchStateBroadcaster(WebSocketConnectionManager webSocketConnectionManager, UUID matchId,
            MatchDto.Factory matchDtoFactory) {
        this.webSocketConnectionManager = webSocketConnectionManager;
        this.matchDtoFactory = matchDtoFactory;
        this.matchId = matchId;
        webSocketConnectionManager.addWelcomeMessage(matchId, this::matchStateMessage);
        webSocketConnectionManager.addWelcomeMessage(matchId, this::turnWillEndAt);
    }

    @SubscribeAll({ EventHandlerRegistered.class, CardResolved.class, CardDestroyed.class, CardPurchased.class,
            CardGained.class, PlayerTookDamage.class, MatchStarted.class, TurnEnded.class, MoneyChanged.class,
            TurnStarted.class })
    private void matchStateChanged(Event event, Match match) {
        broadcastFullMatchState(match);
    }

    @Subscribe
    private void turnWillEndAt(TurnWillEndAt turnWillEndAt) {
        this.turnWillEndAt = turnWillEndAt.getInstant();
        webSocketConnectionManager.broadcast(matchId, turnWillEndAt().orElseThrow());
    }

    @Subscribe
    private void turnEnded(TurnEnded turnEnded) {
        webSocketConnectionManager.broadcast(matchId,
                ServerWebSocketMessages.turnEnded(turnEnded.getPreviousPlayer().getUser()));
    }

    @Subscribe
    private void matchEnded(MatchEnded matchEnded) {
        winner = matchEnded.getWinner();
        webSocketConnectionManager.broadcast(matchId, ServerWebSocketMessages.matchEnded(matchEnded.getWinner()));
    }

    private void broadcastFullMatchState(Match match) {
        latestMatchState = matchDtoFactory.matchDto(match);
        matchStateMessage().ifPresent(m -> webSocketConnectionManager.broadcast(matchId, m));
    }

    private Optional<WebSocketMessage<?>> matchStateMessage() {
        if (winner != null) {
            return Optional.of(ServerWebSocketMessages.matchEnded(winner));
        }
        return Optional.ofNullable(latestMatchState).map(ServerWebSocketMessages::matchState);
    }

    private Optional<WebSocketMessage<Long>> turnWillEndAt() {
        return Optional.ofNullable(turnWillEndAt).map(ServerWebSocketMessages::turnWillEndAt);
    }
}
