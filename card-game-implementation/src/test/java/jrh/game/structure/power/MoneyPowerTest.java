package jrh.game.structure.power;

import org.junit.jupiter.api.Test;

public class MoneyPowerTest {

    @Test
    public void roundTripsViaJson() {
        TestPower.roundTripsViaJson(new MoneyPower(5));
    }
}
