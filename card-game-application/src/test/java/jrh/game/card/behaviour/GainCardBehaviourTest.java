package jrh.game.card.behaviour;

import jrh.game.common.CardId;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class GainCardBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new GainCardBehaviour(new CardId(randomAlphanumeric(10))));
    }

}
