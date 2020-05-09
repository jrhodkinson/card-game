package jrh.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MoneyCard extends Card {

    private static final Logger logger = LogManager.getLogger(MoneyCard.class);

    private final int amount;

    public MoneyCard(int amount) {
        super(0);
        this.amount = amount;
    }

    @Override
    public void play(Match match, Target target) {
        match.getCurrentTurn().setMoney(match.getCurrentTurn().getMoney() + amount);
    }

    @Override
    public String toString() {
        return String.format("%d money", amount);
    }
}
