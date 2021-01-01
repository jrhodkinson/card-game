package jrh.game.structure.power;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.api.Match;
import jrh.game.api.Player;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardPurged;
import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;
import jrh.game.match.TurnController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jrh.game.common.description.Keyword.ACQUIRE;
import static jrh.game.common.description.Keyword.PURGE;

@JsonKey("moneyOnPurge")
public class MoneyOnPurgePower extends AbstractPower {

    private static final Logger logger = LogManager.getLogger(DamageOnPurchasePower.class);

    @JsonValue
    private final int amount;

    public MoneyOnPurgePower(int amount) {
        this.amount = amount;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription
            .builder()
            .keyword(ACQUIRE)
            .money(amount)
            .plainString("whenever you")
            .keyword(PURGE)
            .build();
    }

    @Subscribe
    private void cardPurged(CardPurged cardPurged, Match match) {
        Player owner = getOwner(match);
        if (cardPurged.getPurger().equals(owner) && match.getActivePlayer().equals(owner)) {
            logger.info("Increasing turn's money by {}", amount);
            match.getController(TurnController.class).changeMoney(amount);
        }
    }

    @Override
    public MoneyOnPurgePower duplicate() {
        return new MoneyOnPurgePower(amount);
    }
}
