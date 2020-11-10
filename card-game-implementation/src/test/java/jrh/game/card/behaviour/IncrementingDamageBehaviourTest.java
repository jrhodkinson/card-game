package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

public class IncrementingDamageBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new IncrementingDamageBehaviour(1, 2));
    }

}