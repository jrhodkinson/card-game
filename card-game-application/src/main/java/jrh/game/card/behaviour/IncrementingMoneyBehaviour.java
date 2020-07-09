package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.asset.JsonKey;
import jrh.game.api.event.CardPlayed;
import jrh.game.api.event.CardResolved;
import jrh.game.common.BehaviourDescription;
import jrh.game.api.Callback;
import jrh.game.api.Subscribe;
import jrh.game.match.TurnController;
import jrh.game.api.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("incrementingMoney")
public class IncrementingMoneyBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(IncrementingMoneyBehaviour.class);

    @JsonProperty
    private int amount;

    @JsonProperty
    private final int increment;

    public IncrementingMoneyBehaviour(@JsonProperty("amount") int amount, @JsonProperty("increment") int increment) {
        super(false);
        this.amount = amount;
        this.increment = increment;
    }

    @Override
    public BehaviourDescription getDescription() {
        return BehaviourDescription.builder().keyword("+ $").number(amount).plainString("then increase future $ by")
                .number(increment).build();
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match, Callback callback) {
        if (cardPlayed.getCard().equals(this.getCard())) {
            logger.info("Adding {} money to current turn", amount);
            match.getController(TurnController.class).changeMoney(amount);
        }
    }

    @Subscribe
    private void cardResolved(CardResolved cardResolved, Match match, Callback callback) {
        if (cardResolved.getCard().equals(this.getCard())) {
            logger.info("Increasing money of {} from {} by {}", this.getCard().getEntityId(), amount,
                    amount + increment);
            this.amount += increment;
        }
    }

    @Override
    public IncrementingMoneyBehaviour duplicate() {
        return new IncrementingMoneyBehaviour(amount, increment);
    }
}
