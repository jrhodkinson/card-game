package jrh.game.card.behaviour;

import jrh.game.structure.StructureId;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ConstructBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new ConstructBehaviour(new StructureId("structure-id")));
    }

}