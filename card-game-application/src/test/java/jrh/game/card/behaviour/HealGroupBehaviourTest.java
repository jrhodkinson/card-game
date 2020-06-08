package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jrh.game.match.api.Target.OWN_STRUCTURES;

public class HealGroupBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new HealGroupBehaviour(List.of(OWN_STRUCTURES), 5));
    }

}