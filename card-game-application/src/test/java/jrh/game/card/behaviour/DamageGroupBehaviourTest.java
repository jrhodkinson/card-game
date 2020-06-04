package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jrh.game.match.api.Target.OWN_STRUCTURES;
import static jrh.game.match.api.Target.SELF;

public class DamageGroupBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new DamageGroupBehaviour(List.of(SELF, OWN_STRUCTURES), 2));
    }

}