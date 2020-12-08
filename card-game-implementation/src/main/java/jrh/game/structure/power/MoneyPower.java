package jrh.game.structure.power;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.TurnStarted;
import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Keyword;
import jrh.game.match.TurnController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonKey("money")
public class MoneyPower extends AbstractPower {

    private static final Logger logger = LoggerFactory.getLogger(MoneyPower.class);

    @JsonValue
    private final int amount;

    public MoneyPower(int amount) {
        this.amount = amount;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().plainString("At the start of your turn,").keyword(Keyword.ACQUIRE).money(amount)
                .build();
    }

    @Subscribe
    private void turnStarted(TurnStarted turnStarted, Match match) {
        if (turnStarted.getNewPlayer().equals(getOwner(match))) {
            logger.info("Increasing turn's money by {}", amount);
            match.getController(TurnController.class).changeMoney(amount);
        }
    }

    @Override
    public MoneyPower duplicate() {
        return new MoneyPower(amount);
    }
}
