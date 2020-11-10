package jrh.game.service.lobby;

import jrh.game.api.EventBus;
import jrh.game.api.Match;
import jrh.game.match.MutableMatch;

public class MatchAndEventBus {

    private final Match match;
    private final EventBus eventBus;

    private MatchAndEventBus(Match match, EventBus eventBus) {
        this.match = match;
        this.eventBus = eventBus;
    }

    public static MatchAndEventBus from(MutableMatch mutableMatch) {
        return new MatchAndEventBus(mutableMatch, mutableMatch.getEventBus());
    }

    public Match getMatch() {
        return match;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
