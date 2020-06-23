package jrh.game.match;

import jrh.game.api.MatchActionHandler;
import jrh.game.api.action.Action;
import jrh.game.api.event.Subscribe;
import jrh.game.api.event.impl.ActionReceived;
import jrh.game.common.EventHandler;

public class MatchActionHandlerImpl implements EventHandler, MatchActionHandler {

    private final MutableMatch match;

    MatchActionHandlerImpl(MutableMatch match) {
        this.match = match;
    }

    public void accept(Action action) {
        match.getEventBus().dispatch(new ActionReceived(action));
    }

    @Subscribe
    private void actionReceived(ActionReceived actionReceived) {
        actionReceived.getAction().perform(match);
    }
}
