package jrh.game.card;

import jrh.game.api.Match;
import jrh.game.api.event.CardCreated;
import jrh.game.api.event.CardDestroyed;
import jrh.game.event.Callback;
import jrh.game.common.EventHandler;
import jrh.game.event.Subscribe;

public class CardBehaviourRegistrar implements EventHandler {

    @Subscribe
    private void cardCreated(CardCreated cardCreated, Match match, Callback callback) {
        cardCreated.getCard().getBehaviours().forEach(callback::register);
    }

    @Subscribe
    private void cardDestroyed(CardDestroyed cardDestroyed, Match match, Callback callback) {
        cardDestroyed.getCard().getBehaviours().forEach(callback::unregister);
    }
}
