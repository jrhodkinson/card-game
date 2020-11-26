package jrh.game.service.lobby;

import jrh.game.api.Callback;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.MatchEnded;
import jrh.game.common.EventHandler;

import java.util.UUID;
import java.util.function.Consumer;

public class MatchEndedCallback implements EventHandler {

    private final Consumer<UUID> onEnded;

    public MatchEndedCallback(Consumer<UUID> onEnded) {
        this.onEnded = onEnded;
    }

    @Subscribe
    private void matchEnded(MatchEnded matchEnded, Match match, Callback callback) {
        callback.unregister(this);
        onEnded.accept(match.getId());
    }
}
