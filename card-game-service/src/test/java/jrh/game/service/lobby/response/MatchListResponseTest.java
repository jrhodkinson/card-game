package jrh.game.service.lobby.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.service.lobby.MatchMetadata;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class MatchListResponseTest {

    private final ObjectMapper objectMapper = ObjectMapperFactory.create();

    @Test
    public void serialisesToJson() throws JsonProcessingException, JSONException {
        UUID matchId = UUID.randomUUID();
        String player = randomAlphabetic(10);
        MatchMetadata matchMetadata = new MatchMetadata(matchId, List.of(player));
        MatchListResponse response = new MatchListResponse(List.of(matchMetadata));

        String expectedJson = String.format("{\"matches\":[{\"id\":\"%s\", \"players\":[\"%s\"]}]}", matchId, player);
        String actualJson = objectMapper.writeValueAsString(response);

        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
    }
}
