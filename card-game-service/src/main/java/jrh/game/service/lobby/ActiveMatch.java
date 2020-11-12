package jrh.game.service.lobby;

import jrh.game.api.ActionHandler;
import jrh.game.api.EventBus;
import jrh.game.api.Match;
import jrh.game.match.MutableMatch;

import java.util.UUID;

public class ActiveMatch {

    private final UUID id;
    private final Match match;
    private final EventBus eventBus;

    private ActiveMatch(Match match, EventBus eventBus) {
        this.id = UUID.randomUUID();
        this.match = match;
        this.eventBus = eventBus;
    }

    public static ActiveMatch from(MutableMatch mutableMatch) {
        return new ActiveMatch(mutableMatch, mutableMatch.getEventBus());
    }

    public UUID getId() {
        return id;
    }

    public ActionHandler getActionHandler() {
        return match.getActionHandler();
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
