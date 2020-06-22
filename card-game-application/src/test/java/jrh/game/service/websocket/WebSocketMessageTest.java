package jrh.game.service.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.common.InstanceId;
import jrh.game.common.ObjectMapperFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static jrh.game.service.websocket.WebSocketMessageTypes.PLAY_CARD;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class WebSocketMessageTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = ObjectMapperFactory.create();
    }

    @Test
    public void pingRoundTripsViaJson() throws JsonProcessingException {
        WebSocketMessage<Long> ping = WebSocketMessages.ping();
        String json = objectMapper.writeValueAsString(ping);
        WebSocketMessage<Long> parsed = objectMapper.readValue(json, new TypeReference<>() {});
        assertThat(parsed.getType(), equalTo(ping.getType()));
        assertThat(parsed.getPayload(), equalTo(ping.getPayload()));
    }

    @Test
    public void canParsePlayCardMessage() throws JsonProcessingException {
        InstanceId instanceId = InstanceId.randomInstanceId();
        String playCardMessage = String.format("{\"type\":\"card/play\", \"payload\":\"%s\"}", instanceId);
        WebSocketMessage<InstanceId> parsed = objectMapper.readValue(playCardMessage, new TypeReference<>() {});
        assertThat(parsed.getType(), equalTo(PLAY_CARD));
        assertThat(parsed.getPayload(), equalTo(instanceId));
    }
}
