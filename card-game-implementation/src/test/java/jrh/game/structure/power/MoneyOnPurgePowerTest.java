package jrh.game.structure.power;

import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.hamcrest.MatcherAssert.assertThat;

public class MoneyOnPurgePowerTest {

    @Test
    public void standardTest() {
        TestPower.passesAllStandardTests(new MoneyOnPurgePower(nextInt()));
    }

}
