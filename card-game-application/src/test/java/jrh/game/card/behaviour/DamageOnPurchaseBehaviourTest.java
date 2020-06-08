package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jrh.game.match.api.Target.SELF;

public class DamageOnPurchaseBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new DamageOnPurchaseBehaviour(List.of(SELF), 9));
    }
}
