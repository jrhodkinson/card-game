package jrh.game.card.behaviour;

import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.api.Behaviour;
import jrh.game.api.Card;
import jrh.game.asset.JsonKey;
import jrh.game.card.CardImpl;
import jrh.game.common.CardId;
import jrh.game.common.ObjectMapperFactory;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBehaviour {

    private static final ObjectMapper objectMapper = ObjectMapperFactory.create();

    public static void passesAllStandardTests(Supplier<AbstractBehaviour> behaviourSupplier) {
        TestBehaviour.hasJsonKeyAnnotation(behaviourSupplier.get().getClass());
        TestBehaviour.roundTripsViaJson(behaviourSupplier.get());
        TestBehaviour.duplicatingGivesSameClass(behaviourSupplier.get());
    }

    public static void roundTripsViaJson(AbstractBehaviour behaviour) {
        try {
            Class<? extends AbstractBehaviour> behaviourClass = behaviour.getClass();
            Card card = CardImpl
                .card(new CardId("test"))
                .withName("Test")
                .withCost(1)
                .isPurchasable(true)
                .withBehaviour(behaviour)
                .build();
            Card parsed = objectMapper.readValue(objectMapper.writeValueAsString(card), CardImpl.class);
            Behaviour parsedBehaviour = parsed.getBehaviours(behaviourClass).get(0);
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

    public static void duplicatingGivesSameClass(AbstractBehaviour behaviour) {
        Behaviour duplicatedBehaviour = behaviour.duplicate();
        assertThat(duplicatedBehaviour.getClass(), equalTo(behaviour.getClass()));
    }

    private static void hasJsonKeyAnnotation(Class<? extends Behaviour> behaviourClass) {
        assertTrue(Arrays
            .stream(behaviourClass.getAnnotations())
            .anyMatch(a -> a.annotationType().isAssignableFrom(JsonKey.class)));
    }
}
