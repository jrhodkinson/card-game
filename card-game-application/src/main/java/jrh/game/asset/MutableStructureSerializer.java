package jrh.game.asset;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jrh.game.api.Power;
import jrh.game.structure.MutableStructure;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

public class MutableStructureSerializer extends StdSerializer<MutableStructure> {

    protected MutableStructureSerializer() {
        super(MutableStructure.class);
    }

    @Override
    public void serialize(MutableStructure structure, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", structure.getStructureId());
        gen.writeObjectField("name", structure.getName());
        gen.writeObjectField("health", structure.getHealth());
        gen.writeArrayFieldStart("powers");
        for (Power power : structure.getPowers()) {
            gen.writeStartArray();
            gen.writeString(SerializationKeys.getPowerKey(power.getClass()));
            if (Arrays.stream(power.getClass().getDeclaredFields())
                    .anyMatch(field -> !field.getType().equals(Logger.class)
                            && !field.getType().equals(MutableStructure.class))) {
                gen.writeObject(power);
            }
            gen.writeEndArray();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }

}
