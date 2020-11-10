package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class VanishBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new VanishBehaviour());
    }

}