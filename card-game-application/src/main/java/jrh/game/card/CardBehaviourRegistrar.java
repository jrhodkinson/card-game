package jrh.game.card;

import jrh.game.event.EventHandler;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.CardCreated;
import jrh.game.event.impl.CardDestroyed;
import jrh.game.match.Match;

public class CardBehaviourRegistrar implements EventHandler {

    @Subscribe
    private void cardCreated(CardCreated cardCreated, Match match) {
        cardCreated.getCard().registerBehaviours(match.getEventBus());
    }

    @Subscribe
    private void cardDestroyed(CardDestroyed cardDestroyed, Match match) {
        cardDestroyed.getCard().unregisterBehaviours(match.getEventBus());
    }
}
