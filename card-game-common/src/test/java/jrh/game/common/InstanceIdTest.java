package jrh.game.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class InstanceIdTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    private void setUp() {
        objectMapper = ObjectMapperFactory.create();
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(InstanceId.class).verify();
    }

    @Test
    public void roundTripsViaJson() throws JsonProcessingException {
        InstanceId original = InstanceId.randomInstanceId();
        String json = objectMapper.writeValueAsString(original);
        InstanceId parsed = objectMapper.readValue(json, InstanceId.class);
        assertThat(parsed, equalTo(original));
    }
}
