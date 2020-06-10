package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jrh.game.common.Target.OWN_STRUCTURES;
import static jrh.game.common.Target.SELF;

public class DamageGroupBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new DamageGroupBehaviour(List.of(SELF, OWN_STRUCTURES), 2));
    }

}