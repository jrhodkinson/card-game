package jrh.game.card.behaviour;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CleanseBehaviourTest {

    @Test
    public void standardTests() {
        TestBehaviour.passesAllStandardTests(CleanseBehaviour::new);
    }

}
