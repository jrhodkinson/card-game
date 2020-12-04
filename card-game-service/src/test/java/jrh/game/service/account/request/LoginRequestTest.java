package jrh.game.service.account.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.common.ObjectMapperFactory;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginRequestTest {

    private final ObjectMapper objectMapper = ObjectMapperFactory.create();

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(LoginRequest.class).verify();
    }

    @Test
    public void deserializesFromJson() throws JsonProcessingException {
        LoginRequest loginRequest = objectMapper.readValue("{\"name\":\"jack\",\"password\":\"£!\"}",
                LoginRequest.class);
        assertThat(loginRequest, equalTo(new LoginRequest("jack", "£!")));
    }

}
