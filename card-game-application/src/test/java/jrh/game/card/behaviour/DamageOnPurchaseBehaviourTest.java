package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

public class DamageOnPurchaseBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new DamageOnPurchaseBehaviour(9));
    }
}
