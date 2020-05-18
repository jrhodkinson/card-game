package jrh.game.card.behaviour;

import jrh.game.card.Card;
import jrh.game.event.EventHandler;
import jrh.game.event.bus.Callback;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.PlayedCard;
import jrh.game.match.Match;
import jrh.game.match.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VanishingBehaviour implements Behaviour {

    private static final Logger logger = LogManager.getLogger(VanishingBehaviour.class);

    @Override
    public void play(Match match, Player player, Card card) {
        match.getEventBus().register(new Vanish(card));
    }

    private static class Vanish implements EventHandler {

        private final Card card;

        private Vanish(Card card) {
            this.card = card;
        }

        @Subscribe
        private void playedCard(PlayedCard playedCard, Match match, Callback callback) {
            logger.debug("RX playedCard={}", playedCard);
            if (card.equals(playedCard.getCard())) {
                logger.info("Vanishing played card={}", playedCard.getCard());
                match.getCardFlowController().vanishPlayedCard(card);
                callback.unregister();
            }
        }
    }
}
