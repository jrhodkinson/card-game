package jrh.game.common;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class CardIdTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(CardId.class).verify();
    }

}