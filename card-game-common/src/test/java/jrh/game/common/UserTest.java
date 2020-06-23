package jrh.game.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = ObjectMapperFactory.create();
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(User.class).verify();
    }

    @Test
    public void roundTripsViaJson() throws JsonProcessingException {
        User user = new User(randomAlphanumeric(10));
        String json = objectMapper.writeValueAsString(user);
        User parsed = objectMapper.readValue(json, User.class);
        assertThat(parsed, equalTo(user));
    }
}
