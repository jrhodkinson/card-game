package jrh.game.service.websocket;

import jrh.game.common.User;
import jrh.game.service.lobby.ActiveMatch;

public class WebSocketSession {

    private final String sessionId;
    private final ActiveMatch match;
    private User user;

    public WebSocketSession(String sessionId, ActiveMatch match) {
        this.sessionId = sessionId;
        this.match = match;
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

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return sessionId;
    }
}
