package jrh.game.service.websocket;

import jrh.game.common.User;
import jrh.game.service.lobby.ActiveMatch;

public class WebSocketSession {

    private final String sessionId;
    private final ActiveMatch match;
    private final User user;
    private final boolean isSpectator;

    public WebSocketSession(String sessionId, ActiveMatch match, User user, boolean isSpectator) {
        this.sessionId = sessionId;
        this.match = match;
        this.user = user;
        this.isSpectator = isSpectator;
    }

    public String getSessionId() {
        return sessionId;
    }

    public ActiveMatch getMatch() {
        return match;
    }

    public User getUser() {
        return user;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

    @Override
    public String toString() {
        return sessionId;
    }
}
