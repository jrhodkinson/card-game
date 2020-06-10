package jrh.game.common;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class StructureIdTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(StructureId.class);
    }

}