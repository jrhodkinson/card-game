package jrh.game.structure.power;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DamagePowerTest {

    @Test
    public void standardTests() {
        TestPower.passesAllTests(new DamagePower(6));
    }
}
