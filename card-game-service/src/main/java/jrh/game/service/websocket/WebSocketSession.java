package jrh.game.service.websocket;

import jrh.game.common.User;
import jrh.game.service.lobby.ActiveMatch;

public class WebSocketSession {

    private final String sessionId;
    private final ActiveMatch match;
    private final User user;

    public WebSocketSession(String sessionId, ActiveMatch match, User user) {
        this.sessionId = sessionId;
        this.match = match;
        this.user = user;
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

    @Override
    public String toString() {
        return sessionId;
    }
}
