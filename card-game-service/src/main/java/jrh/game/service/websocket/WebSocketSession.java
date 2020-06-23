package jrh.game.service.websocket;

import jrh.game.api.Match;
import jrh.game.common.User;

public class WebSocketSession {

    private final String sessionId;
    private final Match match;
    private final User user;

    public WebSocketSession(String sessionId, Match match, User user) {
        this.sessionId = sessionId;
        this.match = match;
        this.user = user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Match getMatch() {
        return match;
    }

    public User getUser() {
        return user;
    }
}
