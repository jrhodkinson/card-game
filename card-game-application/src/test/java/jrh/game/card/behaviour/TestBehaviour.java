package jrh.game.card.behaviour;

import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.card.Card;
import jrh.game.card.CardId;
import jrh.game.util.Color;
import jrh.game.util.ObjectMapperFactory;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestBehaviour {

    private static final ObjectMapper objectMapper = ObjectMapperFactory.create();

    public static void roundTripsViaJson(Behaviour behaviour) {
        try {
            Class<? extends Behaviour> behaviourClass = behaviour.getClass();
            Card card = Card.card(new CardId("test")).withName("Test").withCost(1).withColor(Color.BLACK).build();
            card.addBehaviour(behaviour);
            Card parsed = objectMapper.readValue(objectMapper.writeValueAsString(card), Card.class);
            Behaviour parsedBehaviour = parsed.getBehaviour(behaviourClass);
            for (Field field : behaviourClass.getDeclaredFields()) {
                if (field.getType().equals(Logger.class)) {
                    continue;
                }
                field.setAccessible(true);
                assertThat(field.get(behaviour), equalTo(field.get(behaviour)));
            }
        } catch (IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}