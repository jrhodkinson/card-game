package jrh.game.service.websocket.server.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Description;
import jrh.game.common.description.Keyword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;

public class DescriptionDtoTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = ObjectMapperFactory.create();
    }

    @Test
    public void transformsDescriptionToJson() throws JsonProcessingException {
        Description description = Description
                .of(List.of(AtomicDescription.builder().keyword(Keyword.DAMAGE).number(3).build(),
                        AtomicDescription.builder().keyword(Keyword.ACQUIRE).money(2).plainString("ok?").build()));

        String json = objectMapper.writeValueAsString(DescriptionDto.fromDescription(description));
        List<List<Map<String, Object>>> parsed = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertThat(parsed, hasSize(2));

        List<Map<String, Object>> first = parsed.get(0);
        assertThat(first, hasSize(2));
        assertThat(first.get(0).get("token"), equalTo("damage"));
        assertThat(first.get(0).get("context"), equalTo("Remove health from a player or structure's life total"));
        assertThat(first.get(1).get("token"), equalTo("3"));
        assertThat(first.get(1).get("context"), nullValue());

        List<Map<String, Object>> second = parsed.get(1);
        assertThat(second, hasSize(3));
        assertThat(second.get(0).get("token"), equalTo("acquire"));
        assertThat(second.get(0).get("context"), nullValue());
        assertThat(second.get(1).get("token"), equalTo("$2"));
        assertThat(second.get(1).get("context"), nullValue());
        assertThat(second.get(2).get("token"), equalTo("ok?"));
        assertThat(second.get(2).get("context"), nullValue());
    }

}
