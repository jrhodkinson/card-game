package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonProperty;
import jrh.game.api.Callback;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardPlayed;
import jrh.game.api.event.CardResolved;
import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Keyword;
import jrh.game.match.TurnController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.NO_TARGET;

@JsonKey("incrementingMoney")
public class IncrementingMoneyBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(IncrementingMoneyBehaviour.class);

    @JsonProperty
    private int amount;

    @JsonProperty
    private final int increment;

    public IncrementingMoneyBehaviour(@JsonProperty("amount") int amount, @JsonProperty("increment") int increment) {
        super(NO_TARGET);
        this.amount = amount;
        this.increment = increment;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().keyword(Keyword.ACQUIRE).money(amount).plainString("then increase by")
                .money(increment).build();
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
            logger.info("Increasing money of {} from {} to {}", this.getCard().getEntityId(), amount,
                    amount + increment);
            this.amount += increment;
        }
    }

    @Override
    public IncrementingMoneyBehaviour duplicate() {
        return new IncrementingMoneyBehaviour(amount, increment);
    }
}
