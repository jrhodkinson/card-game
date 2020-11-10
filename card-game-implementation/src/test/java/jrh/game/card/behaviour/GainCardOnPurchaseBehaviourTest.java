package jrh.game.card.behaviour;

import jrh.game.common.CardId;
import org.junit.jupiter.api.Test;

public class GainCardOnPurchaseBehaviourTest {

    @Test
    public void roundTripsViaJson() {
        TestBehaviour.roundTripsViaJson(new GainCardOnPurchaseBehaviour(CardId.Debug.DAMAGE));
    }

}
