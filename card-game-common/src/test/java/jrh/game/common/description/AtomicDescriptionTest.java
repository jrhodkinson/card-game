package jrh.game.common.description;

import org.junit.jupiter.api.Test;

import java.util.List;

import static jrh.game.common.Target.OTHER;
import static jrh.game.common.Target.OTHER_STRUCTURES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AtomicDescriptionTest {

    private final DescriptionContext descriptionContext = new DescriptionContext(null);

    @Test
    public void createsDescriptionForSimpleDamageBehaviour() {
        AtomicDescription description = AtomicDescription.builder().keyword(Keyword.DAMAGE).number(3).build();
        assertThat(description.get(descriptionContext), equalTo("Damage 3"));
    }

    @Test
    public void createsDescriptionForOnPurchaseBehaviour() {
        AtomicDescription description = AtomicDescription
            .builder()
            .plainString("On purchase,")
            .keyword(Keyword.DAMAGE)
            .targets(List.of(OTHER, OTHER_STRUCTURES))
            .plainString("by")
            .number(4)
            .build();
        assertThat(description.get(descriptionContext),
                equalTo("On purchase, damage your opponent and your opponent's structures by 4"));
    }
}
