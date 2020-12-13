package jrh.game.card;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.api.Card;
import jrh.game.common.CardId;
import jrh.game.common.ObjectMapperFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CardImplTest {

    private static final ObjectMapper objectMapper = ObjectMapperFactory.create();

    @Test
    public void flavorTextRoundTripsViaJson() throws JsonProcessingException {
        String flavorText = randomAlphanumeric(12);
        Card card = CardImpl.card(new CardId(UUID.randomUUID().toString())).withName("test card").withCost(1)
                .withFlavorText(flavorText).build();
        String json = objectMapper.writeValueAsString(card);
        CardImpl parsedCard = objectMapper.readValue(json, CardImpl.class);
        assertThat(parsedCard.getFlavorText().get(), equalTo(flavorText));
    }
}
