package jrh.game.common.description;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class KeywordTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Keyword.class).verify();
    }

}
