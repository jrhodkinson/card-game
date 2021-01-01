package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.api.Match;
import jrh.game.api.Player;
import jrh.game.api.Subscribe;
import jrh.game.api.event.CardPurchased;
import jrh.game.asset.JsonKey;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Keyword;
import jrh.game.match.CardFlowController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static jrh.game.card.behaviour.AbstractBehaviour.TargetType.NO_TARGET;

@JsonKey("drawOnPurchase")
public class DrawOnPurchaseBehaviour extends AbstractBehaviour {

    private static final Logger logger = LogManager.getLogger(DrawOnPurchaseBehaviour.class);

    @JsonValue
    private final int amount;

    public DrawOnPurchaseBehaviour(int amount) {
        super(NO_TARGET);
        this.amount = amount;
    }

    @Override
    public AtomicDescription getDescription() {
        return AtomicDescription.builder().plainString("On purchase,").keyword(Keyword.DRAW).number(amount).build();
    }

    @Subscribe
    private void cardPurchased(CardPurchased cardPurchased, Match match) {
        if (cardPurchased.getCard().equals(this.getCard())) {
            Player purchaser = cardPurchased.getPurchaser();
            logger.info("Drawing {} card(s) for card purchaser={}", amount, purchaser);
            match.getController(CardFlowController.class).drawCards(purchaser, amount);
        }
    }

    @Override
    public DrawOnPurchaseBehaviour duplicate() {
        return new DrawOnPurchaseBehaviour(amount);
    }
}
