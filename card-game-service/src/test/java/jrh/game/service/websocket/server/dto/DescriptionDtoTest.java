package jrh.game.service.websocket.server.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.common.description.AtomicDescription;
import jrh.game.common.description.Description;
import jrh.game.common.description.DescriptionContext;
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
                        AtomicDescription.builder().keyword(Keyword.ACQUIRE).money(2).plainString("ok").build()));

        DescriptionDto.Factory factory = new DescriptionDto.Factory(new DescriptionContext(null));
        String json = objectMapper.writeValueAsString(factory.descriptionDto(description));
        Map<String, Object> parsed = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertThat(parsed.size(), equalTo(2));
        assertThat(parsed.get("text"), equalTo("Damage 3. Acquire $2 ok."));
        List<List<Map<String, Object>>> lines = (List<List<Map<String, Object>>>) parsed.get("lines");

        List<Map<String, Object>> first = lines.get(0);
        assertThat(first, hasSize(2));
        assertThat(first.get(0).get("token"), equalTo(Keyword.DAMAGE.toString()));
        assertThat(first.get(0).get("context"), equalTo(Keyword.DAMAGE.getHelp()));
        assertThat(first.get(1).get("token"), equalTo("3"));
        assertThat(first.get(1).get("context"), nullValue());
        List<Map<String, Object>> second = lines.get(1);
        assertThat(second, hasSize(3));
        assertThat(second.get(0).get("token"), equalTo(Keyword.ACQUIRE.toString()));
        assertThat(second.get(0).get("context"), equalTo(Keyword.ACQUIRE.getHelp()));
        assertThat(second.get(1).get("token"), equalTo("$2"));
        assertThat(second.get(1).get("context"), nullValue());
        assertThat(second.get(2).get("token"), equalTo("ok"));
        assertThat(second.get(2).get("context"), nullValue());
    }

}
