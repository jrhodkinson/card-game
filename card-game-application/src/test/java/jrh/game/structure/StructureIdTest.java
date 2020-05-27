package jrh.game.structure;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StructureIdTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(StructureId.class);
    }

}