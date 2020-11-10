package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

public class SacrificeBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new SacrificeBehaviour(7));
    }
}
