package jrh.game.service.websocket;

import jrh.game.api.Match;
import jrh.game.common.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WebSocketSession {

    private final String sessionId;
    private final Match match;
    private User user;

    public WebSocketSession(String sessionId, Match match) {
        this.sessionId = sessionId;
        this.match = match;
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

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("sessionId", sessionId)
            .append("user", user)
            .toString();
    }
}
