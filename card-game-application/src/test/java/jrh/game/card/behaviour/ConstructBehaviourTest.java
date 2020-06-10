package jrh.game.card.behaviour;

import jrh.game.common.StructureId;
import org.junit.jupiter.api.Test;

public class ConstructBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new ConstructBehaviour(new StructureId("structure-id")));
    }

}