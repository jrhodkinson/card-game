package jrh.game.structure.power;

import org.junit.jupiter.api.Test;

public class TauntPowerTest {

    @Test
    public void roundTripsViaJson() {
        TestPower.roundTripsViaJson(new TauntPower());
    }
}
