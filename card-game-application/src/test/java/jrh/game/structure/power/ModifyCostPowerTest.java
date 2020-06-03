package jrh.game.structure.power;

import org.junit.jupiter.api.Test;

import java.util.List;

public class ModifyCostPowerTest {

    @Test
    public void roundTripsViaJson() {
        TestPower.roundTripsViaJson(new ModifyCostPower(List.of(Target.SELF, Target.OTHER), 7));
    }

}