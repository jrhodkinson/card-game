package jrh.game.card.behaviour;

import jrh.game.card.TestCard;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomUtils.nextInt;

public class MoneyBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new MoneyBehaviour(nextInt()));
    }

    @Test
    public void addsMoneyToCurrentTurn() {
        MoneyBehaviour moneyBehaviour = new MoneyBehaviour(3);
        TestCard.forBehaviour(moneyBehaviour);
    }
}
