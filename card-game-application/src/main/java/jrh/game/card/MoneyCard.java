package jrh.game.card;

import jrh.game.match.Match;
import jrh.game.match.Target;
import jrh.game.util.Colors;
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
        return String.format("%s%d money (%d)%s", Colors.YELLOW, amount, getCost(), Colors.RESET);
    }
}
