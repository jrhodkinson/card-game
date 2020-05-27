package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.card.event.CardPlayed;
import jrh.game.card.event.CardResolved;
import jrh.game.event.Callback;
import jrh.game.event.Subscribe;
import jrh.game.match.TurnController;
import jrh.game.match.api.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IncrementingMoneyBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(IncrementingMoneyBehaviour.class);

    @JsonProperty
    private int amount;

    @JsonProperty
    private final int increment;

    public IncrementingMoneyBehaviour(@JsonProperty("amount") int amount, @JsonProperty("increment") int increment) {
        this.amount = amount;
        this.increment = increment;
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match, Callback callback) {
        if (cardPlayed.getCard().equals(this.getCard()) && cardPlayed.getTarget().isPresent()) {
            logger.info("Adding {} money to current turn", amount);
            match.getController(TurnController.class).changeMoney(amount);
        }
    }

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match, Callback callback) {
        if (cardResolved.getCard().equals(this.getCard())) {
            logger.info("Increasing money of {} from {} by {}", this.getCard().getInstanceId(), amount,
                    amount + increment);
            this.amount += increment;
        }
    }

    @Override
    public IncrementingMoneyBehaviour duplicate() {
        return new IncrementingMoneyBehaviour(amount, increment);
    }
}
