package jrh.game.asset;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jrh.game.card.Card;
import jrh.game.structure.Structure;
import jrh.game.structure.power.Power;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

public class StructureSerializer extends StdSerializer<Structure> {

    protected StructureSerializer() {
        super(Structure.class);
    }

    @Override
    public void serialize(Structure structure, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", structure.getStructureId());
        gen.writeObjectField("name", structure.getName());
        gen.writeObjectField("health", structure.getHealth());
        gen.writeArrayFieldStart("powers");
        for (Power power : structure.getPowers()) {
            gen.writeStartArray();
            gen.writeString(SerializationKeys.getPowerKey(power.getClass()));
            if (Arrays.stream(power.getClass().getDeclaredFields())
                    .anyMatch(field -> !field.getType().equals(Logger.class) && !field.getType().equals(Card.class))) {
                gen.writeObject(power);
            }
            gen.writeEndArray();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }

}
