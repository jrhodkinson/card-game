package jrh.game.structure.power;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IncrementingDamagePowerTest {

    @Test
    public void roundTripsViaJson() {
        TestPower.roundTripsViaJson(new IncrementingDamagePower(2, 6));
    }

}
