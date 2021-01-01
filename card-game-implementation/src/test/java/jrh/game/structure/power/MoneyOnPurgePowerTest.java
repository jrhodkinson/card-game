package jrh.game.structure.power;

import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomUtils.nextInt;

public class MoneyOnPurgePowerTest {

    @Test
    public void standardTests() {
        TestPower.passesAllStandardTests(() -> new MoneyOnPurgePower(nextInt()));
    }

}
