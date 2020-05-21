package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

public class UnplayableBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new UnplayableBehaviour());
    }
}
