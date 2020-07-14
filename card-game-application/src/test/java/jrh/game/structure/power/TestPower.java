package jrh.game.structure.power;

import com.fasterxml.jackson.databind.ObjectMapper;
import jrh.game.api.Power;
import jrh.game.common.ObjectMapperFactory;
import jrh.game.common.StructureId;
import jrh.game.structure.MutableStructure;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestPower {

    private static final ObjectMapper objectMapper = ObjectMapperFactory.create();

    public static void roundTripsViaJson(AbstractPower power) {
        try {
            Class<? extends AbstractPower> powerClass = power.getClass();
            MutableStructure structure = new MutableStructure(new StructureId("test"), "Test", 1);
            structure.addPower(power);
            MutableStructure parsed = objectMapper.readValue(objectMapper.writeValueAsString(structure),
                    MutableStructure.class);
            Power parsedPower = parsed.getPowers(powerClass).get(0);
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
