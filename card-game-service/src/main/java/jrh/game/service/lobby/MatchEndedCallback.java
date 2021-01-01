package jrh.game.service.lobby;

import jrh.game.api.Callback;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.MatchEnded;
import jrh.game.common.EventHandler;
import jrh.game.common.User;

import java.util.UUID;
import java.util.function.BiConsumer;

public class MatchEndedCallback implements EventHandler {

    private final BiConsumer<UUID, User> onEnded;

    public MatchEndedCallback(BiConsumer<UUID, User> onEnded) {
        this.onEnded = onEnded;
    }

    @Subscribe
    private void matchEnded(MatchEnded matchEnded, Match match, Callback callback) {
        callback.unregister(this);
        onEnded.accept(match.getId(), matchEnded.getWinner());
    }
}
