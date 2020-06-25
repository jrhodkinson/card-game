package jrh.game.service.websocket.client.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.common.InstanceId;
import jrh.game.common.ObjectMapperFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class PlayCardDtoTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = ObjectMapperFactory.create();
    }

    @Test
    public void parsesJson() throws JsonProcessingException {
        InstanceId card = InstanceId.randomInstanceId();
        InstanceId target = InstanceId.randomInstanceId();
        String json = String.format("{\"card\":\"%s\", \"target\": \"%s\"}", card, target);
        PlayCardDto playCardDto = objectMapper.readValue(json, PlayCardDto.class);
        assertThat(playCardDto.getCard(), equalTo(card));
        assertThat(playCardDto.getTarget(), equalTo(target));
    }

    @Test
    public void parsesJsonWithNoTarget() throws JsonProcessingException {
        InstanceId card = InstanceId.randomInstanceId();
        String json = String.format("{\"card\":\"%s\" }", card);
        PlayCardDto playCardDto = objectMapper.readValue(json, PlayCardDto.class);
        assertThat(playCardDto.getCard(), equalTo(card));
        assertThat(playCardDto.getTarget(), is(nullValue()));
    }
}
