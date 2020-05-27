package jrh.game.card;

import jrh.game.card.event.CardCreated;
import jrh.game.card.event.CardDestroyed;
import jrh.game.event.EventHandler;
import jrh.game.event.Subscribe;
import jrh.game.match.api.Match;

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
