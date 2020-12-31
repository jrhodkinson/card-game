package jrh.game.structure.power;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class DamagePowerTest {

    @Test
    public void standardTests() {
        TestPower.passesAllStandardTests(new DamagePower(6));
    }
}
