package jrh.game.structure.power;

import jrh.game.common.Target;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DamageOnPurchasePowerTest {

    @Test
    public void roundTripsViaJson() {
        TestPower.roundTripsViaJson(new DamageOnPurchasePower(List.of(Target.OTHER), 3));
    }
}
