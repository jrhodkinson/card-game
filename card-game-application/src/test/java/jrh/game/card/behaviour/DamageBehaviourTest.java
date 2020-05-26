package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomUtils.nextInt;

public class DamageBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new DamageBehaviour(nextInt()));
    }
}
