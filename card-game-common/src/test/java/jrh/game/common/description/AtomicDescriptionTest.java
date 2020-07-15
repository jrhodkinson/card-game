package jrh.game.common.description;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jrh.game.common.Target.OTHER;
import static jrh.game.common.Target.OTHER_STRUCTURES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AtomicDescriptionTest {

    @Test
    public void createsDescriptionForSimpleDamageBehaviour() {
        AtomicDescription description = AtomicDescription.builder()
            .keyword("Damage")
            .number(3)
            .build();
        assertThat(description.toString(), equalTo("Damage 3"));
    }

    @Test
    public void createsDescriptionForOnPurchaseBehaviour() {
        AtomicDescription description = AtomicDescription.builder()
            .plainString("On purchase,")
            .keyword("damage")
            .plainString(List.of(OTHER, OTHER_STRUCTURES).toString())
            .plainString("by")
            .number(4)
            .build();
        assertThat(description.toString(), equalTo("On purchase, damage [OTHER, OTHER_STRUCTURES] by 4"));
    }
}
