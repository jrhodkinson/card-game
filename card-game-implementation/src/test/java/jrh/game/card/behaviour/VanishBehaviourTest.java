package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

public class VanishBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new VanishBehaviour());
    }

    @Test
    public void duplication() {
        TestBehaviour.duplicatingGivesSameClass(new VanishBehaviour());
    }

}
