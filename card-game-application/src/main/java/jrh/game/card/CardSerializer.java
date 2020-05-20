package jrh.game.card;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jrh.game.card.behaviour.Behaviour;
import jrh.game.card.behaviour.BehaviourSerializationKeys;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

public class CardSerializer extends StdSerializer<Card> {

    protected CardSerializer() {
        super(Card.class);
    }

    @Override
    public void serialize(Card card, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("id", card.getCardId());
        gen.writeObjectField("name", card.getName());
        gen.writeObjectField("cost", card.getCost());
        gen.writeObjectField("color", card.getColor());
        gen.writeArrayFieldStart("behaviours");
        for (Behaviour behaviour : card.getBehaviours()) {
            gen.writeStartArray();
            gen.writeString(BehaviourSerializationKeys.getKey(behaviour.getClass()));
            if (Arrays.stream(behaviour.getClass().getDeclaredFields()).anyMatch(field -> !field.getType().equals(Logger.class) && !field.getType().equals(Card.class))) {
                gen.writeObject(behaviour);
            }
            gen.writeEndArray();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }

}
