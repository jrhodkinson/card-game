package jrh.game.structure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.api.Structure;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.common.StructureId;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StructureTest {

    private static final ObjectMapper objectMapper = ObjectMapperFactory.create();

    @Test
    public void flavorTextRoundTripsViaJson() throws JsonProcessingException {
        String flavorText = randomAlphanumeric(12);
        Structure structure = new MutableStructure(new StructureId("test"), "Test", 1, flavorText);
        String json = objectMapper.writeValueAsString(structure);
        MutableStructure parsedStructure = objectMapper.readValue(json, MutableStructure.class);
        assertThat(parsedStructure.getFlavorText().get(), equalTo(flavorText));
    }
}
