package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.asset.JsonKey;
import jrh.game.card.event.CardPlayed;
import jrh.game.event.Subscribe;
import jrh.game.match.TurnController;
import jrh.game.match.api.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JsonKey("money")
public class MoneyBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(MoneyBehaviour.class);

    @JsonValue
    private final int amount;

    public MoneyBehaviour(int amount) {
        super(false);
        this.amount = amount;
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard())) {
            logger.info("Adding {} money to current turn", amount);
            match.getController(TurnController.class).changeMoney(amount);
        }
    }

    @Override
    public MoneyBehaviour duplicate() {
        return new MoneyBehaviour(amount);
    }
}
