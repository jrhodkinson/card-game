package jrh.game.structure.power;

import org.junit.jupiter.api.Test;

public class ModifyOutgoingDamagePowerTest {

    @Test
    public void roundTripsViaJson() {
        TestPower.roundTripsViaJson(new ModifyOutgoingDamagePower(2));
    }
}
