package jrh.game.service.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.service.websocket.server.ServerWebSocketMessages;
import org.joda.time.DateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static jrh.game.service.websocket.client.ClientWebSocketMessageTypes.PONG;
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
        WebSocketMessage<Long> ping = ServerWebSocketMessages.ping();
        String json = objectMapper.writeValueAsString(ping);
        WebSocketMessage<Long> parsed = objectMapper.readValue(json, new TypeReference<>() {
        });
        assertThat(parsed.getType(), equalTo(ping.getType()));
        assertThat(parsed.getPayload(), equalTo(ping.getPayload()));
    }

    @Test
    public void canParsePongMessage() throws JsonProcessingException {
        long time = DateTimeUtils.currentTimeMillis();
        String pongMessage = String.format("{\"type\":\"pong\", \"payload\":%s}", time);
        WebSocketMessage<Long> parsed = objectMapper.readValue(pongMessage, new TypeReference<>() {
        });
        assertThat(parsed.getType(), equalTo(PONG));
        assertThat(parsed.getPayload(), equalTo(time));
    }
}
