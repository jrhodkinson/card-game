package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.api.Match;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardPurchased;
import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Keyword;
import jrh.game.match.TurnController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.NO_TARGET;

@JsonKey("moneyOnPurchase")
public class MoneyOnPurchaseBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(DamageBehaviour.class);

    @JsonValue
    private final int amount;

    public MoneyOnPurchaseBehaviour(int amount) {
        super(NO_TARGET);
        this.amount = amount;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().plainString("On purchase,").keyword(Keyword.ACQUIRE).number(amount).build();
    }

    @Subscribe
    private void cardPurchased(CardPurchased cardPurchased, Match match) {
        if (cardPurchased.getCard().equals(this.getCard()) && match.getActivePlayer().equals(cardPurchased.getPurchaser())) {
            logger.info("Adding {} money to current turn", amount);
            match.getController(TurnController.class).changeMoney(amount);
        }
    }

    @Override
    public MoneyOnPurchaseBehaviour duplicate() {
        return new MoneyOnPurchaseBehaviour(amount);
    }
}
