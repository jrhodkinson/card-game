package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

public class HealBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new HealBehaviour(6));
    }
}
