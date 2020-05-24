package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.event.bus.Callback;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.CardPlayed;
import jrh.game.event.impl.CardResolved;
import jrh.game.match.Match;
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
            match.getCurrentTurn().setMoney(match.getCurrentTurn().getMoney() + amount);
        }
    }

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match, Callback callback) {
        if (cardResolved.getCard().equals(this.getCard())) {
            logger.info("Increasing money of {} from {} by {}", this.getCard().getInstanceId(), amount, amount + increment);
            this.amount += increment;
        }
    }

    @Override
    public IncrementingMoneyBehaviour duplicate() {
        return new IncrementingMoneyBehaviour(amount, increment);
    }
}
