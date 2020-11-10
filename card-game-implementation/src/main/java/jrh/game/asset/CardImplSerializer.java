package jrh.game.asset;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jrh.game.api.Behaviour;
import jrh.game.card.CardImpl;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

public class CardImplSerializer extends StdSerializer<CardImpl> {

    protected CardImplSerializer() {
        super(CardImpl.class);
    }

    @Override
    public void serialize(CardImpl card, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", card.getCardId());
        gen.writeObjectField("name", card.getName());
        gen.writeObjectField("cost", card.getCost());
        gen.writeObjectField("color", card.getColor());
        if (card.getFlavorText().isPresent()) {
            gen.writeObjectField("flavor", card.getFlavorText().get());
        }
        gen.writeArrayFieldStart("behaviours");
        for (Behaviour behaviour : card.getAllBehaviours()) {
            gen.writeStartArray();
            gen.writeString(SerializationKeys.getBehaviourKey(behaviour.getClass()));
            if (Arrays.stream(behaviour.getClass().getDeclaredFields()).anyMatch(
                    field -> !field.getType().equals(Logger.class) && !field.getType().equals(CardImpl.class))) {
                gen.writeObject(behaviour);
            }
            gen.writeEndArray();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }

}
