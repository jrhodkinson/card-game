package jrh.game.card.behaviour;

import com.fasterxml.jackson.annotation.JsonValue;
import jrh.game.event.bus.Subscribe;
import jrh.game.event.impl.CardPlayed;
import jrh.game.match.Match;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoneyBehaviour extends Behaviour {

    private static final Logger logger = LogManager.getLogger(MoneyBehaviour.class);

    @JsonValue
    private final int amount;

    public MoneyBehaviour(int amount) {
        this.amount = amount;
    }

    @Subscribe
    private void cardPlayed(CardPlayed cardPlayed, Match match) {
        if (cardPlayed.getCard().equals(this.getCard())) {
            logger.info("Adding {} money to current turn", amount);
            match.getCurrentTurn().setMoney(match.getCurrentTurn().getMoney() + amount);
        }
    }

    @Override
    public MoneyBehaviour duplicate() {
        return new MoneyBehaviour(amount);
    }
}
