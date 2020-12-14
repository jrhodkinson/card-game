package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

public class PurgeBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new PurgeBehaviour());
    }

    @Test
    public void duplication() {
        TestBehaviour.duplicatingGivesSameInstance(new PurgeBehaviour());
    }

}
