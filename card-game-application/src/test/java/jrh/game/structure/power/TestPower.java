package jrh.game.structure.power;

import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.structure.MutableStructure;
import jrh.game.structure.StructureId;
import jrh.game.util.ObjectMapperFactory;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestPower {

    private static final ObjectMapper objectMapper = ObjectMapperFactory.create();

    public static void roundTripsViaJson(Power power) {
        try {
            Class<? extends Power> powerClass = power.getClass();
            MutableStructure structure = new MutableStructure(new StructureId("test"), "Test", 1);
            structure.addPower(power);
            MutableStructure parsed = objectMapper.readValue(objectMapper.writeValueAsString(structure), MutableStructure.class);
            Power parsedPower = parsed.getPower(powerClass);
            for (Field field : powerClass.getDeclaredFields()) {
                if (field.getType().equals(Logger.class)) {
                    continue;
                }
                field.setAccessible(true);
                assertThat(field.get(power), equalTo(field.get(power)));
            }
        } catch (IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}